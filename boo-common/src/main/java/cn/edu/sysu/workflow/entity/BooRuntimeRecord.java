package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooRuntimeRecord extends BooPagedQuery {

    private static final long serialVersionUID = -1926465135408134380L;

    private String runtimeRecordId;
    private String processId;
    private String processName;
    private String sessionId;
    private String launchAuthorityId;
    private Timestamp launchTimestamp;
    private String launchFrom;
    private Integer launchType;
    private String tag;
    private String interpreterId;
    private String resourcingId;
    private String resourceBinding;
    private Integer resourceBindingType;
    private Integer failureType;
    private String participantCache;
    private Timestamp finishTimestamp;
    private int isSucceed;

    public BooRuntimeRecord() {
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLaunchAuthorityId() {
        return launchAuthorityId;
    }

    public void setLaunchAuthorityId(String launchAuthorityId) {
        this.launchAuthorityId = launchAuthorityId;
    }

    public Timestamp getLaunchTimestamp() {
        return launchTimestamp;
    }

    public void setLaunchTimestamp(Timestamp launchTimestamp) {
        this.launchTimestamp = launchTimestamp;
    }

    public String getLaunchFrom() {
        return launchFrom;
    }

    public void setLaunchFrom(String launchFrom) {
        this.launchFrom = launchFrom;
    }

    public Integer getLaunchType() {
        return launchType;
    }

    public void setLaunchType(Integer launchType) {
        this.launchType = launchType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInterpreterId() {
        return interpreterId;
    }

    public void setInterpreterId(String interpreterId) {
        this.interpreterId = interpreterId;
    }

    public String getResourcingId() {
        return resourcingId;
    }

    public void setResourcingId(String resourcingId) {
        this.resourcingId = resourcingId;
    }

    public String getResourceBinding() {
        return resourceBinding;
    }

    public void setResourceBinding(String resourceBinding) {
        this.resourceBinding = resourceBinding;
    }

    public Integer getResourceBindingType() {
        return resourceBindingType;
    }

    public void setResourceBindingType(Integer resourceBindingType) {
        this.resourceBindingType = resourceBindingType;
    }

    public Integer getFailureType() {
        return failureType;
    }

    public void setFailureType(Integer failureType) {
        this.failureType = failureType;
    }

    public String getParticipantCache() {
        return participantCache;
    }

    public void setParticipantCache(String participantCache) {
        this.participantCache = participantCache;
    }

    public Timestamp getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(Timestamp finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public int getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(int isSucceed) {
        this.isSucceed = isSucceed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooRuntimeRecord that = (BooRuntimeRecord) o;
        return isSucceed == that.isSucceed &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(processId, that.processId) &&
                Objects.equals(processName, that.processName) &&
                Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(launchAuthorityId, that.launchAuthorityId) &&
                Objects.equals(launchTimestamp, that.launchTimestamp) &&
                Objects.equals(launchFrom, that.launchFrom) &&
                Objects.equals(launchType, that.launchType) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(interpreterId, that.interpreterId) &&
                Objects.equals(resourcingId, that.resourcingId) &&
                Objects.equals(resourceBinding, that.resourceBinding) &&
                Objects.equals(resourceBindingType, that.resourceBindingType) &&
                Objects.equals(failureType, that.failureType) &&
                Objects.equals(participantCache, that.participantCache) &&
                Objects.equals(finishTimestamp, that.finishTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runtimeRecordId, processId, processName, sessionId, launchAuthorityId, launchTimestamp, launchFrom, launchType, tag, interpreterId, resourcingId, resourceBinding, resourceBindingType, failureType, participantCache, finishTimestamp, isSucceed);
    }
}
