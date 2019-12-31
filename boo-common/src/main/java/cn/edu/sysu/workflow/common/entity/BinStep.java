package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Arrays;
import java.util.Objects;

/**
 * BinStep of BooWFMS.
 * 流程实例单步数据快照。
 *
 * @author Skye
 * Created on 2019/12/18
 */
public class BinStep extends BooPagedQuery {

    private static final long serialVersionUID = 2776125695679340149L;
    public static final String PREFIX = "bs-";

    /**
     * 数据库主键
     */
    private String binStepId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * TODO 父节点ID
     */
    private String parentId;

    /**
     * TODO 通知ID
     */
    private String notificationId;

    /**
     * 内容数据
     */
    private byte[] binlog;

    public BinStep() {
        super();
    }

    public String getBinStepId() {
        return binStepId;
    }

    public void setBinStepId(String binStepId) {
        this.binStepId = binStepId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public byte[] getBinlog() {
        return binlog;
    }

    public void setBinlog(byte[] binlog) {
        this.binlog = binlog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinStep binStep = (BinStep) o;
        return Objects.equals(binStepId, binStep.binStepId) &&
                Objects.equals(processInstanceId, binStep.processInstanceId) &&
                Objects.equals(parentId, binStep.parentId) &&
                Objects.equals(notificationId, binStep.notificationId) &&
                Arrays.equals(binlog, binStep.binlog);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(binStepId, processInstanceId, parentId, notificationId);
        result = 31 * result + Arrays.hashCode(binlog);
        return result;
    }
}
