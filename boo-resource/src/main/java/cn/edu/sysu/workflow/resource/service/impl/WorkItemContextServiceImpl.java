package cn.edu.sysu.workflow.resource.service.impl;

import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.TaskItem;
import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.entity.WorkItemListItem;
import cn.edu.sysu.workflow.common.enums.WorkItemResourcingStatus;
import cn.edu.sysu.workflow.common.enums.WorkItemStatus;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.resource.BooResourceApplication;
import cn.edu.sysu.workflow.resource.core.ContextLockManager;
import cn.edu.sysu.workflow.resource.core.api.InterfaceA;
import cn.edu.sysu.workflow.resource.core.context.TaskItemContext;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.dao.*;
import cn.edu.sysu.workflow.resource.service.TaskItemContextService;
import cn.edu.sysu.workflow.resource.service.WorkItemContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link WorkItemContextService}
 *
 * @author Skye
 * Created on 2020/3/18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkItemContextServiceImpl implements WorkItemContextService {

    private static final Logger log = LoggerFactory.getLogger(WorkItemContextServiceImpl.class);

    @Autowired
    private TaskItemDAO taskItemDAO;

    @Autowired
    private WorkItemListItemDAO workItemListItemDAO;

    @Autowired
    private WorkItemListDAO workItemListDAO;

    @Autowired
    private WorkItemDAO workItemDAO;

    @Autowired
    private BusinessObjectDAO businessObjectDAO;

    @Autowired
    @Lazy
    private InterfaceA interfaceA;

    @Autowired
    private TaskItemContextService taskItemContextService;

    @Override
    public MultiValueMap<String, String> generateResponseWorkItem(WorkItemContext workItemContext) {
        MultiValueMap<String, String> retMap = new LinkedMultiValueMap<>();
        WorkItem workItem = workItemContext.getWorkItem();
        String workItemId = workItem.getWorkItemId();
        retMap.add("WorkItemId", workItemId);
        retMap.add("ProcessInstanceId", workItem.getProcessInstanceId());
        retMap.add("CallbackNodeId", workItem.getCallbackNodeId());
        retMap.add("TaskName", workItemContext.getTaskItemContext().getTaskName());
        retMap.add("TaskId", workItemContext.getTaskItemContext().getTaskId());
        retMap.add("Role", workItemContext.getTaskItemContext().getBrole());
        retMap.add("Documentation", workItemContext.getTaskItemContext().getDocumentation());
        retMap.add("Argument", JsonUtil.jsonSerialization(workItemContext.getArgsDict()));
        retMap.add("Status", workItem.getStatus());
        retMap.add("ResourcingStatus", workItem.getResourcingStatus());
        retMap.add("EnablementTime", workItem.getCreateTimestamp() == null ? "" : workItem.getCreateTimestamp().toString());
        retMap.add("AllocateTime", workItem.getAllocateTimestamp() == null ? "" : workItem.getAllocateTimestamp().toString());
        retMap.add("LaunchTime", workItem.getLaunchTimestamp() == null ? "" : workItem.getLaunchTimestamp().toString());
        retMap.add("CompletionTime", workItem.getFinishTimestamp() == null ? "" : workItem.getFinishTimestamp().toString());
        retMap.add("ExecuteTime", String.valueOf(workItem.getExecuteTime()));
        List<WorkItemListItem> relations = null;
        try {
            relations = workItemListItemDAO.findWorkItemListItemsByWorkItemId(workItemId);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + workItem.getProcessInstanceId() + "]GenerateResponseWorkItem but cannot read relation from database, " + ex);
        }
        if (relations == null) {
            retMap.add("WorkerIdList", "[]");
        } else {
            StringBuilder workerIdSb = new StringBuilder();
            workerIdSb.append("[");
            for (WorkItemListItem workItemListItem : relations) {
                String workerId = workItemListDAO.findOwnerAccountIdByWorkItemListId(workItemListItem.getWorkItemListId());
                workerIdSb.append(workerId).append(",");
            }
            String workerIdList = workerIdSb.toString();
            if (workerIdList.length() > 1) {
                workerIdList = workerIdList.substring(0, workerIdList.length() - 1);
            }
            workerIdList += "]";
            retMap.add("WorkerIdList", workerIdList);
        }
        return retMap;
    }

    @Override
    public List<MultiValueMap<String, String>> generateResponseWorkItems(List<WorkItemContext> wList, boolean onlyActive) {
        List<MultiValueMap<String, String>> retList = new ArrayList<>();
        for (WorkItemContext workItemContext : wList) {
            boolean isLock = ContextLockManager.ReadTryLock(workItemContext.getClass(), workItemContext.getWorkItem().getWorkItemId());
            if (onlyActive && !isLock) {
                continue;
            }
            try {
                // 再次获取工作项状态数据
                String status = workItemDAO.findOne(workItemContext.getWorkItem().getWorkItemId()).getStatus();
                if (onlyActive && (status.equals(WorkItemStatus.Complete.name())
                        || status.equals(WorkItemStatus.ForcedComplete.name())
                        || status.equals(WorkItemStatus.Discarded.name()))) {
                    continue;
                }
                retList.add(this.generateResponseWorkItem(workItemContext));
            } finally {
                ContextLockManager.ReadUnLock(workItemContext.getClass(), workItemContext.getWorkItem().getWorkItemId());
            }
        }
        return retList;
    }

    @Override
    public List<WorkItemContext> getContextByProcessInstanceId(String processInstanceId) {
        List<WorkItemContext> retList = new ArrayList<>();
        try {
            List<WorkItem> workItems = workItemDAO.findWorkItemsByProcessInstanceId(processInstanceId);
            for (WorkItem workItem : workItems) {
                retList.add(this.getContext(workItem.getWorkItemId()));
            }
            return retList;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<WorkItemContext> getContextByOrganization(String organization) {
        List<WorkItemContext> retList = new ArrayList<>();
        try {
            List<WorkItem> workItems = workItemDAO.findWorkItemsByOrganization("@" + organization + "_");
            for (WorkItem workItem : workItems) {
                retList.add(this.getContext(workItem.getWorkItemId()));
            }
            return retList;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public WorkItemContext getContext(String workItemId) {
        try {
            WorkItem workItem = workItemDAO.findOne(workItemId);
            if (workItem == null) {
                throw new RuntimeException("WorkItem is NULL!");
            }
            String taskId = workItem.getTaskId();
            TaskItem taskItem = taskItemDAO.findOne(taskId);
            String taskName = taskItem.getTaskPolymorphismName();
            String boId = taskItem.getBusinessObjectId();
            BusinessObject businessObject = businessObjectDAO.findOne(boId);
            String boName = businessObject.getBusinessObjectName();
            WorkItemContext workItemContext = new WorkItemContext();
            workItemContext.setWorkItem(workItem);
            workItemContext.setArgsDict(JsonUtil.jsonDeserialization(workItem.getArguments(), HashMap.class));
            workItemContext.setTaskItemContext(taskItemContextService.getContext(workItem.getProcessInstanceId(), boName, taskName));
            return workItemContext;
        } catch (Exception ex) {
            log.error("[" + workItemId + "]Get work item context but exception occurred, " + ex);
            throw ex;
        }
    }

    @Override
    public WorkItemContext generateContext(TaskItemContext taskItemContext, String processInstanceId, Map args, String callbackNodeId) throws Exception {
        if (args.size() != taskItemContext.getParameters().size()) {
            log.warn(String.format("[%s]Generate work item for task %s, but arguments(%s) and parameters(%s) not equal",
                    processInstanceId, taskItemContext.getTaskName(), args.size(), taskItemContext.getParameters().size()));
        }
        try {
            // 构建work item
            WorkItem workItem = new WorkItem();
            workItem.setWorkItemId(WorkItem.PREFIX + IdUtil.nextId());
            workItem.setProcessInstanceId(processInstanceId);
            workItem.setResourceServiceId(BooResourceApplication.RESOURCE_ID);
            workItem.setProcessId(taskItemContext.getPid());
            workItem.setBusinessObjectId(taskItemContext.getBoid());
            workItem.setTaskId(taskItemContext.getTaskGlobalId());
            workItem.setTaskPolymorphismId(taskItemContext.getTaskId());
            workItem.setStatus(WorkItemStatus.Enabled.name());
            workItem.setResourcingStatus(WorkItemResourcingStatus.Unoffered.name());
            workItem.setExecuteTime(0L);
            workItem.setCallbackNodeId(callbackNodeId);
            workItem.setArguments(JsonUtil.jsonSerialization(args));
            workItemDAO.save(workItem);

            // 构建work item context
            WorkItemContext workItemContext = new WorkItemContext();
            workItemContext.setWorkItem(workItem);
            workItemContext.setArgsDict((HashMap<String, String>) args);
            workItemContext.setTaskItemContext(taskItemContext);
            // handle callback and hook
            interfaceA.handleCallbackAndHook(WorkItemStatus.Enabled, workItemContext, taskItemContext, null);
            return workItemContext;
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + processInstanceId + "]Generate work item context but exception occurred, " + ex);
            throw ex;
        }
    }

    @Override
    public void save(WorkItemContext workItemContext) {
        if (workItemContext == null) {
            log.warn("Ignore null work item context saving.");
            return;
        }
        try {
            workItemDAO.update(workItemContext.getWorkItem());
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + workItemContext.getWorkItem().getProcessInstanceId() + "]Save work item context but exception occurred, " + ex);
        }
    }
}
