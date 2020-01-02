package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.common.enums.FailedWorkItemStatus;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * ExitItem of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class ExitItem extends BooPagedQuery {

    private static final long serialVersionUID = 9072656565769504611L;
    public static final String PREFIX = "ei-";

    /**
     * 数据库主键
     */
    private String exitItemId;

    /**
     * 对应工作项ID
     */
    private String workItemId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 状态
     * @see FailedWorkItemStatus
     */
    private Integer status;

    /**
     * 可见性
     */
    private Integer visibility;

    /**
     * TODO 处理者名称
     */
    private String handlerAuthName;

    /**
     * TODO 时间戳
     */
    private Timestamp timestamp;

    /**
     * TODO 原因
     */
    private String reason;

    public ExitItem() {
        super();
    }

    public String getExitItemId() {
        return exitItemId;
    }

    public void setExitItemId(String exitItemId) {
        this.exitItemId = exitItemId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getHandlerAuthName() {
        return handlerAuthName;
    }

    public void setHandlerAuthName(String handlerAuthName) {
        this.handlerAuthName = handlerAuthName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExitItem exitItem = (ExitItem) o;
        return Objects.equals(exitItemId, exitItem.exitItemId) &&
                Objects.equals(workItemId, exitItem.workItemId) &&
                Objects.equals(processInstanceId, exitItem.processInstanceId) &&
                Objects.equals(status, exitItem.status) &&
                Objects.equals(visibility, exitItem.visibility) &&
                Objects.equals(handlerAuthName, exitItem.handlerAuthName) &&
                Objects.equals(timestamp, exitItem.timestamp) &&
                Objects.equals(reason, exitItem.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exitItemId, workItemId, processInstanceId, status, visibility, handlerAuthName, timestamp, reason);
    }
}
