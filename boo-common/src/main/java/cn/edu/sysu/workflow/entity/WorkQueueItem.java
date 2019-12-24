package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Objects;

/**
 * WorkQueue Item of BooWFMS
 *
 * Created by Skye on 2019/12/24.
 */
public class WorkQueueItem extends BooPagedQuery {

    private static final long serialVersionUID = -5850054634317337384L;
    public static final String PREFIX = "wqi-";

    /**
     * 工作队列项ID
     */
    private String workQueueItemId;

    /**
     * 所属工作队列ID
     */
    private String workQueueId;

    /**
     * 对应工作项ID
     */
    private String workItemId;

    public WorkQueueItem() {
        this.workQueueItemId = PREFIX + IdUtil.nextId();
    }

    public String getWorkQueueItemId() {
        return workQueueItemId;
    }

    public String getWorkQueueId() {
        return workQueueId;
    }

    public void setWorkQueueId(String workQueueId) {
        this.workQueueId = workQueueId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkQueueItem that = (WorkQueueItem) o;
        return Objects.equals(workQueueItemId, that.workQueueItemId) &&
                Objects.equals(workQueueId, that.workQueueId) &&
                Objects.equals(workItemId, that.workItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workQueueItemId, workQueueId, workItemId);
    }
}
