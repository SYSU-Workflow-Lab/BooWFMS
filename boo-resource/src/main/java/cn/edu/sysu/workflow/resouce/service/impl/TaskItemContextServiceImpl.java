package cn.edu.sysu.workflow.resouce.service.impl;

import cn.edu.sysu.workflow.common.entity.TaskItem;
import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.enums.WorkItemStatus;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.resouce.core.context.TaskItemContext;
import cn.edu.sysu.workflow.resouce.dao.ProcessInstanceDAO;
import cn.edu.sysu.workflow.resouce.dao.TaskItemDAO;
import cn.edu.sysu.workflow.resouce.service.TaskItemContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link TaskItemContextService}
 *
 * @author Skye
 * Created on 2020/3/18
 */
@Service
public class TaskItemContextServiceImpl implements TaskItemContextService {

    private static final Logger log = LoggerFactory.getLogger(TaskItemContextServiceImpl.class);

    @Autowired
    private TaskItemDAO taskItemDAO;

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    @Override
    public TaskItemContext parseHashMap(Map mapObj) {
        TaskItemContext taskItemContext = new TaskItemContext();
        taskItemContext.setTaskGlobalId((String) mapObj.get("taskGlobalId"));
        taskItemContext.setTaskId((String) mapObj.get("taskId"));
        taskItemContext.setTaskName((String) mapObj.get("taskName"));
        taskItemContext.setPrinciple((String) mapObj.get("principle"));
        taskItemContext.setBrole((String) mapObj.get("brole"));
        taskItemContext.setBoid((String) mapObj.get("boid"));
        taskItemContext.setPid((String) mapObj.get("pid"));
        taskItemContext.setDocumentation((String) mapObj.get("documentation"));
        taskItemContext.setHooks((HashMap<String, ArrayList<String>>) mapObj.get("notifyHooks"));
        taskItemContext.setCallbacks((HashMap<String, ArrayList<String>>) mapObj.get("callbackEvents"));
        taskItemContext.setParameters((ArrayList<String>) mapObj.get("parameters"));
        return taskItemContext;
    }

    @Override
    public List<String> getCallbackEventsOfStatus(TaskItemContext taskItemContext, WorkItemStatus statusType) {
        List<String> retList = taskItemContext.getCallbackEvents().get(statusType.name().toUpperCase());
        return retList == null ? new ArrayList<>() : retList;
    }

    @Override
    public List<String> getCallbackHooksOfStatus(TaskItemContext taskItemContext, WorkItemStatus statusType) {
        List<String> retList = taskItemContext.getNotifyHooks().get(statusType.name().toUpperCase());
        return retList == null ? new ArrayList<>() : retList;
    }


    /**
     * Parse hooks by a descriptor in entity.
     *
     * @param hookJSONDescriptor JSON descriptor
     */
    public void parseHooks(TaskItemContext taskItemContext, String hookJSONDescriptor) {
        taskItemContext.setHooks(new HashMap<>());
        Map<String, ArrayList<String>> deMap = JsonUtil.jsonDeserialization(hookJSONDescriptor, HashMap.class);
        for (Map.Entry<String, ArrayList<String>> kvp : deMap.entrySet()) {
            taskItemContext.getNotifyHooks().put(kvp.getKey().toUpperCase(), kvp.getValue());
        }
    }

    /**
     * Parse callback events by a descriptor in entity.
     *
     * @param callbackJSONDescriptor JSON descriptor
     */
    public void parseCallbacks(TaskItemContext taskItemContext, String callbackJSONDescriptor) {
        taskItemContext.setCallbacks(new HashMap<>());
        Map<String, ArrayList<String>> deMap = JsonUtil.jsonDeserialization(callbackJSONDescriptor, HashMap.class);
        for (Map.Entry<String, ArrayList<String>> kvp : deMap.entrySet()) {
            taskItemContext.getCallbackEvents().put(kvp.getKey().toUpperCase(), kvp.getValue());
        }
    }

    /**
     * Parse parameter vector by a descriptor in entity.
     *
     * @param parametersDescriptor parameter string descriptor
     */
    public void parseParameters(TaskItemContext taskItemContext, String parametersDescriptor) {
        Map<String, String> paras = JsonUtil.jsonDeserialization(parametersDescriptor, HashMap.class);
        taskItemContext.setParameters(new ArrayList<>());
        taskItemContext.getParameters().addAll(paras.keySet());
    }

    /**
     * Generate a task context by a entity entity.
     *
     * @param taskItem task item entity
     * @param pid          Belong to process global id
     * @return equivalent task context.
     */
    public TaskItemContext generateTaskItemContext(TaskItem taskItem, String pid) {
        TaskItemContext context = new TaskItemContext(taskItem.getTaskPolymorphismId(), taskItem.getTaskPolymorphismName(),
                taskItem.getBusinessRole(), pid, taskItem.getBusinessObjectId(),
                taskItem.getPrinciple(), taskItem.getDocumentation());
        String hookDescriptor = taskItem.getHookDescriptor();
        if (!StringUtils.isEmpty(hookDescriptor)) {
            this.parseHooks(context, hookDescriptor);
        }
        String eventDescriptor = taskItem.getEventDescriptor();
        if (!StringUtils.isEmpty(eventDescriptor)) {
            this.parseCallbacks(context, eventDescriptor);
        }
        String parametersDescriptor = taskItem.getParameters();
        if (!StringUtils.isEmpty(parametersDescriptor)) {
            this.parseParameters(context, parametersDescriptor);
        }
        context.setTaskGlobalId(taskItem.getTaskItemId());
        return context;
    }

    @Override
    public TaskItemContext getContext(String processInstanceId, String businessObjectName, String taskName) {
        try {
            TaskItem taskItem = taskItemDAO.findByPIIDAndBONAndTaskName(processInstanceId, businessObjectName, taskName);
            String businessProcessId = processInstanceDAO.findOne(processInstanceId).getProcessId();
            return this.generateTaskItemContext(taskItem, businessProcessId);
        } catch (Exception ex) {
            log.error("[" + processInstanceId + "]When json serialization exception occurred, transaction rollback. " + ex);
            return null;
        }
    }

    @Override
    public TaskItemContext getContext(WorkItem workItem) {
        try {
            TaskItem taskItem = taskItemDAO.findByPIIdAndBOIdAndTaskId(workItem.getProcessInstanceId(), workItem.getBusinessObjectId(), workItem.getTaskPolymorphismId());
            String businessProcessId = processInstanceDAO.findOne(workItem.getProcessInstanceId()).getProcessId();
            return this.generateTaskItemContext(taskItem, businessProcessId);
        } catch (Exception ex) {
            log.error("[" + workItem.getWorkItemId() + "]When json serialization exception occurred, transaction rollback. " + ex);
            return null;
        }
    }
}
