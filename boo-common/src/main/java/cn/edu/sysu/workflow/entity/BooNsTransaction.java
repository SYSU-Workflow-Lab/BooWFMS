package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooNsTransaction extends BooPagedQuery {

    private static final long serialVersionUID = -665537756794775422L;

    private String nsTransactionId;
    private Integer type;
    private String runtimeRecordId;
    private int priority;
    private String context;
    private Timestamp acceptTimestamp;
    private Timestamp finishTimestamp;
    private String requestInvoker;
    private Timestamp scheduledTimestamp;

    public BooNsTransaction() {
    }

    public String getNsTransactionId() {
        return nsTransactionId;
    }

    public void setNsTransactionId(String nsTransactionId) {
        this.nsTransactionId = nsTransactionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Timestamp getAcceptTimestamp() {
        return acceptTimestamp;
    }

    public void setAcceptTimestamp(Timestamp acceptTimestamp) {
        this.acceptTimestamp = acceptTimestamp;
    }

    public Timestamp getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(Timestamp finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public String getRequestInvoker() {
        return requestInvoker;
    }

    public void setRequestInvoker(String requestInvoker) {
        this.requestInvoker = requestInvoker;
    }

    public Timestamp getScheduledTimestamp() {
        return scheduledTimestamp;
    }

    public void setScheduledTimestamp(Timestamp scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooNsTransaction that = (BooNsTransaction) o;
        return priority == that.priority &&
                Objects.equals(nsTransactionId, that.nsTransactionId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(context, that.context) &&
                Objects.equals(acceptTimestamp, that.acceptTimestamp) &&
                Objects.equals(finishTimestamp, that.finishTimestamp) &&
                Objects.equals(requestInvoker, that.requestInvoker) &&
                Objects.equals(scheduledTimestamp, that.scheduledTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nsTransactionId, type, runtimeRecordId, priority, context, acceptTimestamp, finishTimestamp, requestInvoker, scheduledTimestamp);
    }
}
