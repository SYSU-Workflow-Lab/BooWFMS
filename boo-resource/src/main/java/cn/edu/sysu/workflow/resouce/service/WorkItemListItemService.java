package cn.edu.sysu.workflow.resouce.service;

import cn.edu.sysu.workflow.common.entity.WorkItemList;
import cn.edu.sysu.workflow.common.entity.WorkItemListItem;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;

import java.util.List;

/**
 * 工作项列表项管理服务
 *
 * @author Skye
 * Created on 2020/3/14
 */
public interface WorkItemListItemService {

    /**
     * Add or update a work item to this work item list.
     *
     * @param workItemList    work item list to be modified
     * @param workItemContext work item data to be handled
     */
    void addOrUpdate(WorkItemList workItemList, WorkItemContext workItemContext);

    /**
     * Add or update a work item to this work item list.
     *
     * @param workItemList
     * @param another
     */
    void addAnotherWorkItemList(WorkItemList workItemList, WorkItemList another);

    /**
     * Remove a work item from this work item list.
     *
     * @param workItemList    work item list to be modified
     * @param workItemContext workItem request context
     */
    void removeByWorkItemContext(WorkItemList workItemList, WorkItemContext workItemContext);

    /**
     * Remove all entries from a specific process instance.
     *
     * @param workItemList      work item list to be modified
     * @param processInstanceId process instance id
     */
    void removeByProcessInstanceId(WorkItemList workItemList, String processInstanceId);

    /**
     * Remove a work item from all work item list.
     *
     * @param workItemContext work item context
     */
    void removeByWorkItemId(WorkItemContext workItemContext);

    /**
     * Check this work item list is empty.
     *
     * @param workItemList work item list to be checked
     * @return true if queue empty
     */
    boolean isEmpty(WorkItemList workItemList);

    /**
     * Count the work item list size.
     *
     * @param workItemList work item list to be counted
     * @return number of work items in this queue
     */
    int count(WorkItemList workItemList);

    /**
     * Check if a work item in work item list.
     *
     * @param workItemList work item list to be counted
     * @param workItemId   work item global id
     * @return true if exist in work item list
     */
    boolean contains(WorkItemList workItemList, String workItemId);

    /**
     * Get a work item from this work item list by its global id.
     *
     * @param workItemList work item list to be counted
     * @param workItemId   work item global id
     * @return true if exist in work item list
     */
    WorkItemListItem retrieve(WorkItemList workItemList, String workItemId);

    /**
     * 查找工作项列表所有数据
     *
     * @param workItemList work item list to be got
     * @return
     */
    List<WorkItemListItem> findWorkItemListItemsByWorkItemListId(WorkItemList workItemList);

}
