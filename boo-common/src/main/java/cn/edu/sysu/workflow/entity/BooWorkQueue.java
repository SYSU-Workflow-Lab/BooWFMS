package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooWorkQueue extends BooPagedQuery {

    private static final long serialVersionUID = -108247111739202171L;

    private String workQueueId;
    private String ownerId;
    private int type;

    public BooWorkQueue() {
    }

    public String getWorkQueueId() {
        return workQueueId;
    }

    public void setWorkQueueId(String workQueueId) {
        this.workQueueId = workQueueId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooWorkQueue that = (BooWorkQueue) o;
        return type == that.type &&
                Objects.equals(workQueueId, that.workQueueId) &&
                Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workQueueId, ownerId, type);
    }
}
