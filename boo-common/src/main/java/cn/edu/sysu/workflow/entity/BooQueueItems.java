package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooQueueItems extends BooPagedQuery {

    private static final long serialVersionUID = -5850054634317337384L;

    private String queueItemsId;
    private String workqueueId;
    private String workitemId;

    public BooQueueItems() {
    }

    public String getQueueItemsId() {
        return queueItemsId;
    }

    public void setQueueItemsId(String queueItemsId) {
        this.queueItemsId = queueItemsId;
    }

    public String getWorkqueueId() {
        return workqueueId;
    }

    public void setWorkqueueId(String workqueueId) {
        this.workqueueId = workqueueId;
    }

    public String getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooQueueItems that = (BooQueueItems) o;
        return Objects.equals(queueItemsId, that.queueItemsId) &&
                Objects.equals(workqueueId, that.workqueueId) &&
                Objects.equals(workitemId, that.workitemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queueItemsId, workqueueId, workitemId);
    }
}
