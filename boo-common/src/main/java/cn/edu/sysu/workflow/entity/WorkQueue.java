package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.enums.WorkQueueType;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Objects;

/**
 * WorkQueue of BooWFMS.
 *
 * Created by Skye on 2019/12/24.
 */
public class WorkQueue extends BooPagedQuery {

    private static final long serialVersionUID = -108247111739202171L;
    public static final String PREFIX = "wq-";

    /**
     * 工作队列ID
     */
    private String workQueueId;

    /**
     * TODO 拥有者ID
     */
    private String ownerId;

    /**
     * 类型
     * @see WorkQueueType
     */
    private int type;

    public WorkQueue() {
        this.workQueueId = PREFIX + IdUtil.nextId();
    }

    public String getWorkQueueId() {
        return workQueueId;
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
        WorkQueue workQueue = (WorkQueue) o;
        return type == workQueue.type &&
                Objects.equals(workQueueId, workQueue.workQueueId) &&
                Objects.equals(ownerId, workQueue.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workQueueId, ownerId, type);
    }
}
