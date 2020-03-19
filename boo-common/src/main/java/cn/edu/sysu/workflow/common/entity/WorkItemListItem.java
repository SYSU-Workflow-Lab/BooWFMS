package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * WorkItemList Item of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/24
 */
public class WorkItemListItem extends BooPagedQuery {

    private static final long serialVersionUID = -5850054634317337384L;
    public static final String PREFIX = "wili-";

    /**
     * 工作项列表项ID
     */
    private String WorkItemListItemId;

    /**
     * 所属工作项列表ID
     */
    private String WorkItemListId;

    /**
     * 对应工作项ID
     */
    private String workItemId;

    public WorkItemListItem() {
        super();
    }

    public String getWorkItemListItemId() {
        return WorkItemListItemId;
    }

    public void setWorkItemListItemId(String workItemListItemId) {
        WorkItemListItemId = workItemListItemId;
    }

    public String getWorkItemListId() {
        return WorkItemListId;
    }

    public void setWorkItemListId(String workItemListId) {
        WorkItemListId = workItemListId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkItemListItem that = (WorkItemListItem) o;
        return Objects.equals(WorkItemListItemId, that.WorkItemListItemId) &&
                Objects.equals(WorkItemListId, that.WorkItemListId) &&
                Objects.equals(workItemId, that.workItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(WorkItemListItemId, WorkItemListId, workItemId);
    }
}
