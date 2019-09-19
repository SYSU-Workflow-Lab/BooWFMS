package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class BooExitItem extends BooPagedQuery {

    private static final long serialVersionUID = 9072656565769504611L;

    private String exitItemId;
    private String workitemId;
    private String runtimeRecordId;
    private int status;
    private int visibility;
    private String handlerAuthName;
    private Timestamp timestamp;
    private String reason;

    public String getExitItemId() {
        return exitItemId;
    }

    public void setExitItemId(String exitItemId) {
        this.exitItemId = exitItemId;
    }

    public String getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooExitItem that = (BooExitItem) o;
        return status == that.status &&
                visibility == that.visibility &&
                Objects.equals(exitItemId, that.exitItemId) &&
                Objects.equals(workitemId, that.workitemId) &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(handlerAuthName, that.handlerAuthName) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exitItemId, workitemId, runtimeRecordId, status, visibility, handlerAuthName, timestamp, reason);
    }
}
