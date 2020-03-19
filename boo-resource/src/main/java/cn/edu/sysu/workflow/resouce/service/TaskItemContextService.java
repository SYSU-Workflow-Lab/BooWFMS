package cn.edu.sysu.workflow.resouce.service;

import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.enums.WorkItemStatus;
import cn.edu.sysu.workflow.resouce.core.context.TaskItemContext;

import java.util.List;
import java.util.Map;

/**
 * 任务项管理服务
 *
 * @author Skye
 * Created on 2020/3/14
 */
public interface TaskItemContextService {

    /**
     * Parse hash map into task item.
     *
     * @param mapObj mapped object
     * @return task item context object
     */
    TaskItemContext parseHashMap(Map mapObj);

    /**
     * Get callback events by status type.
     *
     * @param statusType status enum
     * @return List of callback event name
     */
    List<String> getCallbackEventsOfStatus(TaskItemContext taskItemContext, WorkItemStatus statusType);

    /**
     * Get callback hooks by status type.
     *
     * @param statusType status enum
     * @return List of callback event name
     */
    List<String> getCallbackHooksOfStatus(TaskItemContext taskItemContext, WorkItemStatus statusType);

    /**
     * Get a task context by its name and belonging BO name of one process instance.
     *
     * @param processInstanceId     process instance id
     * @param businessObjectName   belong to BO name
     * @param taskName task polymorphism name
     * @return Task resourcing context, null if exception occurred or assertion error
     */
    TaskItemContext getContext(String processInstanceId, String businessObjectName, String taskName);

    /**
     * Get a task context by work item.
     *
     * @param workItem     work item
     * @return Task resourcing context, null if exception occurred or assertion error
     */
    TaskItemContext getContext(WorkItem workItem);

}
