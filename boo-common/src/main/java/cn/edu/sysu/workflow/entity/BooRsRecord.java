package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooRsRecord extends BooPagedQuery {

    private static final long serialVersionUID = 4662499467550218808L;

    private String rsRecordId;
    private String runtimeRecordId;
    private String resourcingId;
    private Timestamp receiveTimestamp;
    private Timestamp scheduledTimestamp;
    private Timestamp finishTimestamp;
    private Long executionTimespan;
    private int isSucceed;
    private int priority;
    private String service;
    private String args;

    public BooRsRecord() {
    }

    public String getRsRecordId() {
        return rsRecordId;
    }

    public void setRsRecordId(String rsRecordId) {
        this.rsRecordId = rsRecordId;
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
    }

    public String getResourcingId() {
        return resourcingId;
    }

    public void setResourcingId(String resourcingId) {
        this.resourcingId = resourcingId;
    }

    public Timestamp getReceiveTimestamp() {
        return receiveTimestamp;
    }

    public void setReceiveTimestamp(Timestamp receiveTimestamp) {
        this.receiveTimestamp = receiveTimestamp;
    }

    public Timestamp getScheduledTimestamp() {
        return scheduledTimestamp;
    }

    public void setScheduledTimestamp(Timestamp scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }

    public Timestamp getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(Timestamp finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public Long getExecutionTimespan() {
        return executionTimespan;
    }

    public void setExecutionTimespan(Long executionTimespan) {
        this.executionTimespan = executionTimespan;
    }

    public int getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(int isSucceed) {
        this.isSucceed = isSucceed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooRsRecord that = (BooRsRecord) o;
        return isSucceed == that.isSucceed &&
                priority == that.priority &&
                Objects.equals(rsRecordId, that.rsRecordId) &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(resourcingId, that.resourcingId) &&
                Objects.equals(receiveTimestamp, that.receiveTimestamp) &&
                Objects.equals(scheduledTimestamp, that.scheduledTimestamp) &&
                Objects.equals(finishTimestamp, that.finishTimestamp) &&
                Objects.equals(executionTimespan, that.executionTimespan) &&
                Objects.equals(service, that.service) &&
                Objects.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rsRecordId, runtimeRecordId, resourcingId, receiveTimestamp, scheduledTimestamp, finishTimestamp, executionTimespan, isSucceed, priority, service, args);
    }
}
