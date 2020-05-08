package cn.edu.sysu.workflow.resource.core.api;

import cn.edu.sysu.workflow.common.entity.*;
import cn.edu.sysu.workflow.common.enums.*;
import cn.edu.sysu.workflow.common.util.AuthDomainHelper;
import cn.edu.sysu.workflow.common.util.TimestampUtil;
import cn.edu.sysu.workflow.resource.BooResourceApplication;
import cn.edu.sysu.workflow.resource.core.ContextLockManager;
import cn.edu.sysu.workflow.resource.core.context.TaskItemContext;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.core.executor.AllocateInteractionExecutor;
import cn.edu.sysu.workflow.resource.core.executor.OfferInteractionExecutor;
import cn.edu.sysu.workflow.resource.core.plugin.AgentNotifyPlugin;
import cn.edu.sysu.workflow.resource.core.plugin.AsyncPluginRunner;
import cn.edu.sysu.workflow.resource.core.principle.Principle;
import cn.edu.sysu.workflow.resource.core.principle.PrincipleParser;
import cn.edu.sysu.workflow.resource.dao.AccountDAO;
import cn.edu.sysu.workflow.resource.dao.BusinessProcessDAO;
import cn.edu.sysu.workflow.resource.dao.ProcessInstanceDAO;
import cn.edu.sysu.workflow.resource.dao.WorkItemDAO;
import cn.edu.sysu.workflow.resource.service.ApplicationPerformanceMonitorService;
import cn.edu.sysu.workflow.resource.service.WorkItemContextService;
import cn.edu.sysu.workflow.resource.service.WorkItemListItemService;
import cn.edu.sysu.workflow.resource.service.WorkItemListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Set;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Implementation of Interface B of Resource Service.
 * Interface B is responsible for control work items life-cycle, and provide
 * work item list operations for participants.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceB {

    private static final Logger log = LoggerFactory.getLogger(InterfaceB.class);

    @Autowired
    private ApplicationPerformanceMonitorService applicationPerformanceMonitorService;

    @Autowired
    @Lazy
    private InterfaceA interfaceA;

    @Autowired
    private InterfaceO interfaceO;

    @Autowired
    private InterfaceX interfaceX;

    @Autowired
    private WorkItemDAO workItemDAO;

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    @Autowired
    private BusinessProcessDAO businessProcessDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private WorkItemContextService workItemContextService;

    @Autowired
    private WorkItemListService workItemListService;

    @Autowired
    private WorkItemListItemService workItemListItemService;

    /**
     * Handle perform submit task.
     *
     * @param taskItemContext
     * @param nodeId
     * @param argMap
     * @param processInstanceId
     * @throws Exception
     */
    public void performEngineSubmitTask(TaskItemContext taskItemContext, String nodeId, HashMap argMap, String processInstanceId) throws Exception {
        TaskItemContext taskContext = taskItemContext;

        // use runtime record to get the admin auth name for admin work item list identifier
        String accountId = processInstanceDAO.findOne(processInstanceId).getLaunchAccountId();
        String domain = AuthDomainHelper.getDomainByAuthName(accountDAO.findSimpleOne(accountId).getUsername());

        // generate work item
        WorkItemContext workItemContext = workItemContextService.generateContext(
                taskContext, processInstanceId, (HashMap) argMap.get("taskArgumentsVector"), nodeId);
        ContextLockManager.WriteLock(WorkItemContext.class, workItemContext.getWorkItem().getWorkItemId());

        try {
            // parse resourcing principle
            Principle principle = PrincipleParser.Parse(taskContext.getPrinciple());
            if (principle == null) {
                log.error(String.format("[%s]Cannot parse principle %s", processInstanceId, taskContext.getPrinciple()));
                interfaceX.principleParseFailedRedirectToDomainPool(workItemContext);
                return;
            }
            // get valid resources
            Set<ProcessParticipant> validParticipants =
                    interfaceO.getParticipantByBRole(processInstanceId, taskContext.getBrole());
            if (validParticipants.isEmpty()) {
                log.warn("[" + processInstanceId + "]A task cannot be allocated to any valid resources, so it will be put into admin unoffered work item list.");
                // move work item to admin unoffered work item list
                workItemListService.addToWorkItemList(workItemContext, BooResourceApplication.WORK_ITEM_LIST_ADMIN_PREFIX + domain, WorkItemListType.UNOFFERED);
                return;
            }
            switch (principle.getDistributionType()) {
                case Allocate:
                    // create an allocate interaction
                    AllocateInteractionExecutor allocateInteraction = new AllocateInteractionExecutor(
                            taskContext.getTaskId(), InitializationType.SYSTEM_INITIATED);
                    // create an allocator for task principle
                    allocateInteraction.bindingAllocator(principle, processInstanceId);
                    // do allocate to select a participant for handle this work item
                    ProcessParticipant chosenOne = allocateInteraction.performAllocation(validParticipants, workItemContext);
                    // put work item to the chosen participant allocated work item list
                    workItemListService.addToWorkItemList(workItemContext, chosenOne.getAccountId(), WorkItemListType.ALLOCATED);
                    // change work item status
                    workItemContext.getWorkItem().setAllocateTimestamp(TimestampUtil.getCurrentTimestamp());
                    this.workItemChanged(workItemContext, WorkItemStatus.Fired, WorkItemResourcingStatus.Allocated, null);
                    // notify if agent
                    if (chosenOne.getType() == ProcessParticipantType.Agent.ordinal()) {
                        AgentNotifyPlugin allocateAnp = new AgentNotifyPlugin();
                        HashMap<String, String> allocateNotifyMap = new HashMap<>(workItemContextService.generateResponseWorkItem(workItemContext));
                        allocateAnp.addNotification(chosenOne, allocateNotifyMap, processInstanceId);
                        AsyncPluginRunner.AsyncRun(allocateAnp);
                    }
                    applicationPerformanceMonitorService.getWorkItemCount().incrementAndGet();
                    break;
                case Offer:
                    // create a filter interaction
                    OfferInteractionExecutor offerInteraction = new OfferInteractionExecutor(
                            taskContext.getTaskId(), InitializationType.SYSTEM_INITIATED);
                    // create a filter for task principle
                    offerInteraction.bindingFilter(principle, processInstanceId);
                    // do filter to select a set of participants for this work item
                    Set<ProcessParticipant> chosenSet = offerInteraction.performOffer(validParticipants, workItemContext);
                    // put work item to chosen participants offered work item list
                    AgentNotifyPlugin offerAnp = new AgentNotifyPlugin();
                    HashMap<String, String> offerNotifyMap = new HashMap<>(workItemContextService.generateResponseWorkItem(workItemContext));
                    for (ProcessParticipant oneInSet : chosenSet) {
                        workItemListService.addToWorkItemList(workItemContext, oneInSet.getAccountId(), WorkItemListType.OFFERED);
                        // notify if agent
                        if (oneInSet.getType() == ProcessParticipantType.Agent.ordinal()) {
                            offerAnp.addNotification(oneInSet, offerNotifyMap, processInstanceId);
                        }
                    }
                    // change work item status
                    workItemContext.getWorkItem().setAllocateTimestamp(TimestampUtil.getCurrentTimestamp());
                    this.workItemChanged(workItemContext, WorkItemStatus.Fired, WorkItemResourcingStatus.Offered, null);
                    // do notify
                    if (offerAnp.count(processInstanceId) > 0) {
                        AsyncPluginRunner.AsyncRun(offerAnp);
                    }
                    applicationPerformanceMonitorService.getWorkItemCount().incrementAndGet();
                    break;
                case AutoAllocateIfOfferFailed:
                    // todo not implementation
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemContext.getWorkItem().getWorkItemId());
        }
    }

    /**
     * Handle perform submit task.
     *
     * @param processInstanceId
     * @param successFlag
     */
    public void performEngineFinishProcess(String processInstanceId, String successFlag) {
        try {
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            processInstance.setFinishTimestamp(TimestampUtil.getCurrentTimestamp());
            processInstance.setResultType(Integer.parseInt(successFlag));
            processInstanceDAO.update(processInstance);
            BusinessProcess businessProcess = businessProcessDAO.findOne(processInstance.getProcessId());
            businessProcess.setSuccessCount(businessProcess.getSuccessCount() + 1);
            businessProcessDAO.update(businessProcess);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + processInstanceId + "]PerformEngineFinishProcess but exception occurred, " + ex);
        }
    }

    /**
     * Handle a participant accept a work item.
     *
     * @param participant     participant context
     * @param workItemContext work item context
     * @param initType        initialization type, a flag for engine internal call
     * @param payload         payload in JSON encoded string
     * @return true for a successful work item accept
     */
    public boolean acceptOfferedWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload, InitializationType initType, String tokenId) {
        // remove from all work item list
        workItemListItemService.removeByWorkItemId(workItemContext);
        // if internal call, means accept and start
        if (initType == InitializationType.SYSTEM_INITIATED) {
            // write an allocated event without notification
            this.workItemChanged(workItemContext, WorkItemStatus.Fired, WorkItemResourcingStatus.Allocated, payload, false);
            boolean result = this.startWorkItem(participant, workItemContext, payload, tokenId);
            if (!result) {
                interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "AcceptOffered by System but failed to start");
                return false;
            }
        }
        // otherwise work item should be put to allocated work item list
        else {
            workItemListService.moveFromOfferedToAllocated(workItemContext, participant.getAccountId());
            this.workItemChanged(workItemContext, WorkItemStatus.Fired, WorkItemResourcingStatus.Allocated, payload);
        }
        // todo notify if agent
        return true;
    }

    /**
     * Handle a participant deallocate a work item.
     *
     * @param participant participant context
     * @param workItemContext    work item context
     * @param payload     payload in JSON encoded string
     * @return true for a successful work item deallocate
     */
    public boolean deallocateWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload) {
        try {
            workItemListService.moveFromAllocatedToOffered(workItemContext, participant.getAccountId());
            this.workItemChanged(workItemContext, WorkItemStatus.Fired, WorkItemResourcingStatus.Offered, payload);
            applicationPerformanceMonitorService.getWorkItemCount().decrementAndGet();
            return true;
        } catch (Exception ex) {
            interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Deallocate but exception occurred: " + ex);
            return false;
        }
    }

    /**
     * Handle a participant start a work item.
     *
     * @param participant     participant context
     * @param workItemContext work item context
     * @param payload         payload in JSON encoded string
     * @return true for a successful work item start
     */
    public boolean startWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload, String tokenId) {
        try {
            workItemListService.moveFromAllocatedToStarted(workItemContext, participant.getAccountId());
            WorkItem workItem = workItemContext.getWorkItem();
            workItem.setLastLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
            workItem.setLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
            workItem.setLaunchAccountId(participant.getAccountId());
            workItemDAO.update(workItem);
            // already started
            if (workItemContext.getWorkItem().getStatus().equals(WorkItemStatus.Executing.name())) {
                this.workItemChanged(workItemContext, WorkItemStatus.Executing, WorkItemResourcingStatus.Started, payload);
                return true;
            }
            // start by admin
            if (workItemContext.getWorkItem().getResourcingStatus().equals(WorkItemResourcingStatus.Unoffered.name())) {
                // get admin work item list for this auth user
                String adminQueuePostfix = tokenId.split("_")[1];
                workItemListService.removeFromWorkItemList(workItemContext, BooResourceApplication.WORK_ITEM_LIST_ADMIN_PREFIX + adminQueuePostfix, WorkItemListType.UNOFFERED);
            }
            this.workItemChanged(workItemContext, WorkItemStatus.Executing, WorkItemResourcingStatus.Started, payload);
            return true;
        } catch (Exception ex) {
            log.error("[" + workItemContext.getWorkItem().getProcessInstanceId() + "ParticipantStart get Runtime record failed. " + ex);
            return false;
        }
    }

    /**
     * Handle a participant reallocate a work item.
     *
     * @param participant participant context
     * @param workItemContext    work item context
     * @param payload     payload in JSON encoded string
     * @return true for a successful work item reallocate
     */
    public boolean reallocateWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload) {
        try {
            workItemListService.moveFromStartedToAllocated(workItemContext, participant.getAccountId());
            this.workItemChanged(workItemContext, WorkItemStatus.Fired, WorkItemResourcingStatus.Allocated, payload);
            applicationPerformanceMonitorService.getWorkItemCount().incrementAndGet();
            return true;
        } catch (Exception ex) {
            interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Reallocate but exception occurred: " + ex);
            return false;
        }
    }

    /**
     * Handle a participant suspend a work item.
     *
     * @param participant participant context
     * @param workItemContext    work item context
     * @param payload     payload in JSON encoded string
     * @return true for a successful work item suspend
     */
    public boolean suspendWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload) {
        try {
            workItemListService.moveFromStartedToSuspend(workItemContext, participant.getAccountId());
            this.workItemChanged(workItemContext, WorkItemStatus.Suspended, WorkItemResourcingStatus.Suspended, payload);
            applicationPerformanceMonitorService.getWorkItemCount().decrementAndGet();
            return true;
        } catch (Exception ex) {
            interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Suspend but exception occurred: " + ex);
            return false;
        }
    }

    /**
     * Handle a participant unsuspend a work item.
     *
     * @param participant participant context
     * @param workItemContext    work item context
     * @param payload     payload in JSON encoded string
     * @return true for a successful work item unsuspend
     */
    public boolean unsuspendWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload) {
        try {
            workItemListService.moveFromSuspendToStarted(workItemContext, participant.getAccountId());
            this.workItemChanged(workItemContext, WorkItemStatus.Executing, WorkItemResourcingStatus.Started, payload);
            applicationPerformanceMonitorService.getWorkItemCount().incrementAndGet();
            return true;
        } catch (Exception ex) {
            interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Unsuspend but exception occurred: " + ex);
            return false;
        }
    }

    /**
     * Handle a participant skip a work item.
     *
     * @param participant participant context
     * @param workItemContext    work item context
     * @param payload     payload in JSON encoded string
     * @return true for a successful work item skip
     */
    public boolean skipWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload) {
        try {
            workItemListService.removeFromWorkItemList(workItemContext, participant.getAccountId(), WorkItemListType.ALLOCATED);
            this.workItemChanged(workItemContext, WorkItemStatus.ForcedComplete, WorkItemResourcingStatus.Skipped, payload);
            log.info("Worker[" + participant.getAccountId() + "] handles the work item["
                    + workItemContext.getWorkItem().getWorkItemId() + "] at the stage of " + ResourceEventType.skip.name() + ".");
            applicationPerformanceMonitorService.getWorkItemCount().decrementAndGet();
            return true;
        } catch (Exception ex) {
            interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Skip but exception occurred: " + ex);
            return false;
        }
    }

    /**
     * Handle a participant complete a work item.
     *
     * @param participant participant context
     * @param workItemContext    work item context
     * @param payload     payload in JSON encoded string
     * @return true for a successful work item complete
     */
    public boolean completeWorkItem(ProcessParticipant participant, WorkItemContext workItemContext, String payload) {
        try {
            WorkItem workItem = workItemContext.getWorkItem();
            Timestamp currentTS = TimestampUtil.getCurrentTimestamp();
            Timestamp startTS = workItem.getLaunchTimestamp();
            workItem.setExecuteTime(currentTS.getTime() - startTS.getTime());
            workItem.setFinishTimestamp(currentTS);
            workItem.setFinishAccountId(participant.getAccountId());
            workItemDAO.update(workItem);
            workItemListService.removeFromWorkItemList(workItemContext, participant.getAccountId(), WorkItemListType.STARTED);
            this.workItemChanged(workItemContext, WorkItemStatus.Complete, WorkItemResourcingStatus.Completed, payload);
            log.info("Worker[" + participant.getAccountId() + "] handles the work item["
                    + workItemContext.getWorkItem().getWorkItemId() + "] at the stage of " + ResourceEventType.complete.name() + ".");
            applicationPerformanceMonitorService.getWorkItemCount().decrementAndGet();
            return true;
        } catch (Exception ex) {
            interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Complete but exception occurred: " + ex);
            return false;
        }
    }

    /**
     * Change a work item from one status to another status.
     *
     * @param workItemContext           work item context
     * @param toStatus           destination status
     * @param toResourcingStatus destination resourcing status
     * @param payload            payload in JSON encoded string
     */
    private void workItemChanged(WorkItemContext workItemContext, WorkItemStatus toStatus, WorkItemResourcingStatus toResourcingStatus, String payload) {
        this.workItemChanged(workItemContext, toStatus, toResourcingStatus, payload, true);
    }

    /**
     * Change a work item from one status to another status.
     *
     * @param workItemContext           work item context
     * @param toStatus           destination status
     * @param toResourcingStatus destination resourcing status
     * @param payload            payload in JSON encoded string
     * @param notify             whether need to process callback and hook
     */
    private void workItemChanged(WorkItemContext workItemContext, WorkItemStatus toStatus, WorkItemResourcingStatus toResourcingStatus, String payload, boolean notify) {
        if (toStatus != null) {
            workItemContext.getWorkItem().setStatus(toStatus.name());
        }
        if (toResourcingStatus != null) {
            workItemContext.getWorkItem().setResourcingStatus(toResourcingStatus.name());
        }
        workItemDAO.update(workItemContext.getWorkItem());
        // handle callbacks and hooks
        if (notify) {
            try {
                interfaceA.handleCallbackAndHook(toStatus, workItemContext, workItemContext.getTaskItemContext(), payload);
            } catch (Exception ex) {
                log.error(String.format("[%s]WorkItem(%s) status changed but failed to handle callbacks and hooks, %s",
                        workItemContext.getWorkItem().getProcessInstanceId(), workItemContext.getWorkItem().getWorkItemId(), ex));
                interfaceX.notifyException(workItemContext);
            }
        }
    }
}