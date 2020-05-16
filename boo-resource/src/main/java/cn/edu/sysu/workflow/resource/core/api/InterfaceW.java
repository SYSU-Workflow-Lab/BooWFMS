package cn.edu.sysu.workflow.resource.core.api;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.enums.InitializationType;
import cn.edu.sysu.workflow.common.enums.WorkItemListType;
import cn.edu.sysu.workflow.common.enums.WorkItemResourcingStatus;
import cn.edu.sysu.workflow.resource.core.ContextLockManager;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.dao.ProcessParticipantDAO;
import cn.edu.sysu.workflow.resource.service.WorkItemContextService;
import cn.edu.sysu.workflow.resource.service.WorkItemListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * Author: Rinkako
 * Date  : 2018/2/21
 * Usage : Implementation of Interface W of Resource Service.
 * Interface W is responsible for providing services for outside clients.
 * User sub-systems use this interface for manage work work item lists.
 * Usually methods in the interface will return result immediately.
 */

@Service
public class InterfaceW {

    private static final Logger log = LoggerFactory.getLogger(InterfaceW.class);

    @Autowired
    @Lazy
    private InterfaceB interfaceB;

    @Autowired
    private InterfaceO interfaceO;

    @Autowired
    private InterfaceX interfaceX;

    @Autowired
    private WorkItemContextService workItemContextService;

    @Autowired
    private WorkItemListService workItemListService;

    @Autowired
    private ProcessParticipantDAO processParticipantDAO;

    /**
     * Accept offer a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @param tokenId
     * @return true for a successful work item accept
     */
    public boolean acceptOffer(String workItemId, String workerId, String payload, String tokenId) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Offered)) {
                log.error(String.format("Try to accept work item(%s) but not at Offered status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when AcceptOffer");
                }
                return false;
            }
            return interfaceB.acceptOfferedWorkItem(participant, workItemContext, payload, InitializationType.USER_INITIATED, tokenId);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Deallocate a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @return true for a successful work item deallocate
     */
    public boolean deallocate(String workItemId, String workerId, String payload) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Allocated)) {
                log.error(String.format("Try to deallocate work item(%s) but not at Allocated status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Deallocate");
                }
                return false;
            }
            return interfaceB.deallocateWorkItem(participant, workItemContext, payload);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Start a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @param tokenId
     * @return true for a successful work item start
     */
    public boolean start(String workItemId, String workerId, String payload, String tokenId) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Allocated)) {
                log.error(String.format("Try to start work item(%s) but not at Allocated status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Start");
                }
                return false;
            }
            return interfaceB.startWorkItem(participant, workItemContext, payload, tokenId);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Reallocate a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @return true for a successful work item reallocate
     */
    public boolean reallocate(String workItemId, String workerId, String payload) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Started)) {
                log.error(String.format("Try to reallocate work item(%s) but not at Started status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Reallocate");
                }
                return false;
            }
            return interfaceB.reallocateWorkItem(participant, workItemContext, payload);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Accept and start a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @param tokenId
     * @return true for a successful work item accept and start
     */
    public boolean acceptAndStart(String workItemId, String workerId, String payload, String tokenId) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Offered)) {
                log.error(String.format("Try to accept and start work item(%s) but not at Offered status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when AcceptAndStart");
                }
                return false;
            }
            return interfaceB.acceptOfferedWorkItem(participant, workItemContext, payload, InitializationType.SYSTEM_INITIATED, tokenId);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Skip a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @return true for a successful work item skip
     */
    public boolean skip(String workItemId, String workerId, String payload) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Allocated)) {
                log.error(String.format("Try to skip work item(%s) but not at Allocated status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Skip");
                }
                return false;
            }
            return interfaceB.skipWorkItem(participant, workItemContext, payload);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Suspend a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @return true for a successful work item suspend
     */
    public boolean suspend(String workItemId, String workerId, String payload) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Started)) {
                log.error(String.format("Try to suspend work item(%s) but not at Started status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Suspend");
                }
                return false;
            }
            return interfaceB.suspendWorkItem(participant, workItemContext, payload);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Unsuspend a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @return true for a successful work item unsuspend
     */
    public boolean unsuspend(String workItemId, String workerId, String payload) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Suspended)) {
                log.error(String.format("Try to unsuspend work item(%s) but not at Suspended status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Unsuspend");
                }
                return false;
            }
            return interfaceB.unsuspendWorkItem(participant, workItemContext, payload);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Complete a work item.
     *
     * @param workItemId
     * @param workerId
     * @param payload
     * @return true for a successful work item complete
     */
    public boolean complete(String workItemId, String workerId, String payload) {
        ContextLockManager.WriteLock(WorkItemContext.class, workItemId);
        try {
            WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
            if (!workItemContext.isAtResourcingStatus(WorkItemResourcingStatus.Started)) {
                log.error(String.format("Try to complete work item(%s) but not at Started status", workItemId));
                return false;
            }
            ProcessParticipant participant = processParticipantDAO.findByAccountId(workerId);
            if (participant == null) {
                if (interfaceO.senseParticipantDataChanged(workItemContext.getWorkItem().getProcessInstanceId())) {
                    interfaceX.handleFastFail(workItemContext.getWorkItem().getProcessInstanceId());
                } else {
                    interfaceX.failedRedirectToLauncherDomainPool(workItemContext, "Participant not exist when Complete");
                }
                return false;
            }
            return interfaceB.completeWorkItem(participant, workItemContext, payload);
        } finally {
            ContextLockManager.WriteUnLock(WorkItemContext.class, workItemId);
        }
    }

    /**
     * Get all work items in a specific type of work item list of a worker.
     *
     * @param processInstanceId
     * @param workerId
     * @param queueTypeName
     * @return work item descriptors string in list
     */
    public Set<WorkItem> getWorkItemList(String processInstanceId, String workerId, String queueTypeName) {
        WorkItemListType wqType;
        try {
            wqType = WorkItemListType.valueOf(queueTypeName.toUpperCase());
        } catch (Exception ex) {
            log.error("[" + processInstanceId + "]Illegal queue type: " + queueTypeName);
            throw ex;
        }
        List<WorkItemContext> queueSet = workItemListService.getWorkItemListItems(workerId, wqType);
        Set<WorkItem> retSet = new HashSet<>();
        for (WorkItemContext workItemContext : queueSet) {
            retSet.add(workItemContext.getWorkItem());
        }
        return retSet;
    }

    /**
     * Get all work items in a specific type of list of a list of workers.
     *
     * @param workerIds
     * @param type
     * @return work item descriptors string in map (workerId, list of work item descriptor)
     */
    public Map<String, Set<WorkItem>> getWorkItemLists(String[] workerIds, String type) {
        Map<String, Set<WorkItem>> retMap = new HashMap<>();
        for (String workerId : workerIds) {
            WorkItemListType wqType = WorkItemListType.valueOf(type.toUpperCase());
            List<WorkItemContext> queueSet = workItemListService.getWorkItemListItems(workerId, wqType);
            Set<WorkItem> retSet = new HashSet<>();
            for (WorkItemContext workItemContext : queueSet) {
                retSet.add(workItemContext.getWorkItem());
            }
            retMap.put(workerId, retSet);
        }
        return retMap;
    }

    /**
     * Get all active(means not complete) work items belong to a processInstanceId in user-friendly package.
     *
     * @param processInstanceId
     * @return List of Map of work item data to return
     */
    public List<MultiValueMap<String, String>> getAllActiveWorkItemsInUserFriendly(String processInstanceId) {
        List<WorkItemContext> workItemContextList = workItemContextService.getContextByProcessInstanceId(processInstanceId);
        if (workItemContextList == null) {
            log.error("Cannot get work item for processInstanceId: " + processInstanceId);
            return null;
        }
        return workItemContextService.generateResponseWorkItems(workItemContextList, true);
    }

    /**
     * Get all work items belong to a organization in user-friendly package.
     *
     * @param organization
     * @return List of Map of work item data to return
     */
    public List<MultiValueMap<String, String>> getAllWorkItemsInUserFriendlyByOrganization(String organization) {
        List<WorkItemContext> workItemContextList = workItemContextService.getContextByOrganization(organization);
        if (workItemContextList == null) {
            log.error("Cannot get work item for Organization: " + organization);
            return null;
        }
        return workItemContextService.generateResponseWorkItems(workItemContextList, false);
    }

    /**
     * Get all work items belong to a participant in user-friendly package.
     *
     * @param workerId
     * @return List of Map of work item data to return
     */
    public List<MultiValueMap<String, String>> getAllWorkItemsInUserFriendlyByParticipant(String workerId) {
        List<WorkItemContext> workItemContextList = workItemListService.getWorkListedWorkItems(workerId);
        return workItemContextService.generateResponseWorkItems(workItemContextList, false);
    }

    /**
     * Get a work item in user-friendly package.
     *
     * @param workItemId
     * @return List of Map of work item data to return
     */
    public MultiValueMap<String, String> getWorkItemInFriendly(String workItemId) {
        WorkItemContext workItemContext = workItemContextService.getContext(workItemId);
        if (workItemContext == null) {
            log.error("Cannot get work item for workItemId: " + workItemId);
            return null;
        }
        return workItemContextService.generateResponseWorkItem(workItemContext);
    }

}
