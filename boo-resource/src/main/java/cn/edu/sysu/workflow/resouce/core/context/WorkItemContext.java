package cn.edu.sysu.workflow.resouce.core.context;

import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.enums.WorkItemResourcingStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Author: Rinkako, Skye
 * Date  : 2018/2/4
 * Usage : Work item context is an encapsulation of WorkItem in a
 * convenient way for resourcing service.
 */
public class WorkItemContext implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Steady entity.
     */
    private WorkItem workItem;

    /**
     * Argument dictionary.
     */
    private HashMap<String, String> argsDict;

    /**
     * Template task.
     */
    private TaskItemContext taskItemContext;

    /**
     * Create a new work item context.
     * Private constructor for preventing create context outside.
     */
    public WorkItemContext() {
    }

    /**
     * Get work item entity.
     *
     * @return work item entity object
     */
    public WorkItem getWorkItem() {
        return workItem;
    }

    public void setWorkItem(WorkItem workItem) {
        this.workItem = workItem;
    }

    /**
     * Get work item argument dictionary.
     *
     * @return parameter-argument hash map
     */
    public HashMap<String, String> getArgsDict() {
        return this.argsDict;
    }

    public void setArgsDict(HashMap<String, String> argsDict) {
        this.argsDict = argsDict;
    }

    /**
     * Get the template task item context of this work item.
     *
     * @return TaskItem
     */
    public TaskItemContext getTaskItemContext() {
        return this.taskItemContext;
    }

    public void setTaskItemContext(TaskItemContext taskItemContext) {
        this.taskItemContext = taskItemContext;
    }

    /**
     * Check if this work item at a specific resourcing status.
     *
     * @param workItemResourcingStatus status to be checked
     * @return true if in this resourcing status
     */
    public boolean isAtResourcingStatus(WorkItemResourcingStatus workItemResourcingStatus) {
        return workItemResourcingStatus.name().equalsIgnoreCase(this.workItem.getResourcingStatus());
    }
}
