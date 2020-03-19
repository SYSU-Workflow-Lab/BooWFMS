package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.common.enums.WorkItemListType;

import java.util.Objects;

/**
 * WorkItemList of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/24
 */
public class WorkItemList extends BooPagedQuery {

    private static final long serialVersionUID = -108247111739202171L;
    public static final String PREFIX = "wil-";

    /**
     * 工作项列表ID
     */
    private String workItemListId;

    /**
     * 拥有者ID
     */
    private String ownerAccountId;

    /**
     * 类型
     * @see WorkItemListType
     */
    private Integer type;

    public WorkItemList() {
        super();
    }

    public String getWorkItemListId() {
        return workItemListId;
    }

    public void setWorkItemListId(String workItemListId) {
        this.workItemListId = workItemListId;
    }

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkItemList that = (WorkItemList) o;
        return Objects.equals(workItemListId, that.workItemListId) &&
                Objects.equals(ownerAccountId, that.ownerAccountId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workItemListId, ownerAccountId, type);
    }
}
