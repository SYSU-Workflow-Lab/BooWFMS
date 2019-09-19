package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooWorkItem extends BooPagedQuery {

    private static final long serialVersionUID = 3955119742711773965L;

    private String workItemId;
    private String runtimeRecordId;
    private String resourcingId;
    private String processId;
    private String boId;
    private String taskid;
    private String taskPolymorphismId;
    private String arguments;
    private Timestamp firingTime;
    private Timestamp enablementTime;
    private Timestamp startTime;
    private Timestamp completionTime;
    private String status;
    private String resourceStatus;
    private String startedBy;
    private String completedBy;
    private String timertrigger;
    private String timerexpiry;
    private Timestamp latestStartTime;
    private long executeTime;
    private String callbackNodeId;

    public BooWorkItem() {
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskPolymorphismId() {
        return taskPolymorphismId;
    }

    public void setTaskPolymorphismId(String taskPolymorphismId) {
        this.taskPolymorphismId = taskPolymorphismId;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public Timestamp getFiringTime() {
        return firingTime;
    }

    public void setFiringTime(Timestamp firingTime) {
        this.firingTime = firingTime;
    }

    public Timestamp getEnablementTime() {
        return enablementTime;
    }

    public void setEnablementTime(Timestamp enablementTime) {
        this.enablementTime = enablementTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Timestamp completionTime) {
        this.completionTime = completionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public String getTimertrigger() {
        return timertrigger;
    }

    public void setTimertrigger(String timertrigger) {
        this.timertrigger = timertrigger;
    }

    public String getTimerexpiry() {
        return timerexpiry;
    }

    public void setTimerexpiry(String timerexpiry) {
        this.timerexpiry = timerexpiry;
    }

    public Timestamp getLatestStartTime() {
        return latestStartTime;
    }

    public void setLatestStartTime(Timestamp latestStartTime) {
        this.latestStartTime = latestStartTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getCallbackNodeId() {
        return callbackNodeId;
    }

    public void setCallbackNodeId(String callbackNodeId) {
        this.callbackNodeId = callbackNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooWorkItem that = (BooWorkItem) o;
        return executeTime == that.executeTime &&
                Objects.equals(workItemId, that.workItemId) &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(resourcingId, that.resourcingId) &&
                Objects.equals(processId, that.processId) &&
                Objects.equals(boId, that.boId) &&
                Objects.equals(taskid, that.taskid) &&
                Objects.equals(taskPolymorphismId, that.taskPolymorphismId) &&
                Objects.equals(arguments, that.arguments) &&
                Objects.equals(firingTime, that.firingTime) &&
                Objects.equals(enablementTime, that.enablementTime) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(completionTime, that.completionTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(resourceStatus, that.resourceStatus) &&
                Objects.equals(startedBy, that.startedBy) &&
                Objects.equals(completedBy, that.completedBy) &&
                Objects.equals(timertrigger, that.timertrigger) &&
                Objects.equals(timerexpiry, that.timerexpiry) &&
                Objects.equals(latestStartTime, that.latestStartTime) &&
                Objects.equals(callbackNodeId, that.callbackNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workItemId, runtimeRecordId, resourcingId, processId, boId, taskid, taskPolymorphismId, arguments, firingTime, enablementTime, startTime, completionTime, status, resourceStatus, startedBy, completedBy, timertrigger, timerexpiry, latestStartTime, executeTime, callbackNodeId);
    }
}
