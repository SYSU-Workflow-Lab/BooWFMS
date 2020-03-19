package cn.edu.sysu.workflow.resouce.service;

import cn.edu.sysu.workflow.resouce.core.context.TaskItemContext;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;

import java.util.List;
import java.util.Map;

/**
 * WorkItemContext Handler.
 *
 * @author Skye
 * Created on 2020/3/18
 */
public interface WorkItemContextService {

    /**
     * Generate a user-friendly work item package.
     *
     * @param workItemContext work item context
     * @return HashMap of work item descriptor
     */
    Map<String, String> generateResponseWorkItem(WorkItemContext workItemContext);

    /**
     * Generate a list of user-friendly work item packages.
     *
     * @param wList      list of work item context
     * @param onlyActive whether only get active work items
     * @return ArrayList of HashMap of work item descriptor
     */
    List<Map<String, String>> generateResponseWorkItems(List<WorkItemContext> wList, boolean onlyActive);

    /**
     * Get work item context by processInstanceId.
     *
     * @param processInstanceId process instance id
     * @return ArrayList of work item context
     */
    List<WorkItemContext> getContextByProcessInstanceId(String processInstanceId);

    /**
     * Get work item context by organization.
     *
     * @param organization organization name
     * @return ArrayList of work item context
     */
    List<WorkItemContext> getContextByOrganization(String organization);

    /**
     * Get an exist work item context.
     *
     * @param workItemId        work item global id
     * @param processInstanceId process instance id
     * @return work item context
     */
    WorkItemContext getContext(String workItemId, String processInstanceId);

    /**
     * Generate a work item context and save it to entity by a task context.
     *
     * @param taskItemContext
     * @param processInstanceId
     * @param args
     * @param callbackNodeId
     * @return
     * @throws Exception
     */
    WorkItemContext generateContext(TaskItemContext taskItemContext, String processInstanceId, Map args, String callbackNodeId) throws Exception;

    /**
     * Save changes context to database.
     *
     * @param workItemContext context to be saved
     */
    void save(WorkItemContext workItemContext);
}
