package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooProcess extends BooPagedQuery {

    private static final long serialVersionUID = 6470788893684196731L;

    private String processId;
    private String processName;
    private String mainBo;
    private String creatorRenid;
    private Timestamp createTimestamp;
    private int launchCount;
    private int successCount;
    private Timestamp lastLaunchTimestamp;
    private long averageCost;
    private int state;
    private int authtype;
    private String selfsignature;

    public BooProcess() {
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

    public String getMainBo() {
        return mainBo;
    }

    public void setMainBo(String mainBo) {
        this.mainBo = mainBo;
    }

    public String getCreatorRenid() {
        return creatorRenid;
    }

    public void setCreatorRenid(String creatorRenid) {
        this.creatorRenid = creatorRenid;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public int getLaunchCount() {
        return launchCount;
    }

    public void setLaunchCount(int launchCount) {
        this.launchCount = launchCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public Timestamp getLastLaunchTimestamp() {
        return lastLaunchTimestamp;
    }

    public void setLastLaunchTimestamp(Timestamp lastLaunchTimestamp) {
        this.lastLaunchTimestamp = lastLaunchTimestamp;
    }

    public long getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(long averageCost) {
        this.averageCost = averageCost;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAuthtype() {
        return authtype;
    }

    public void setAuthtype(int authtype) {
        this.authtype = authtype;
    }

    public String getSelfsignature() {
        return selfsignature;
    }

    public void setSelfsignature(String selfsignature) {
        this.selfsignature = selfsignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooProcess that = (BooProcess) o;
        return launchCount == that.launchCount &&
                successCount == that.successCount &&
                averageCost == that.averageCost &&
                state == that.state &&
                authtype == that.authtype &&
                Objects.equals(processId, that.processId) &&
                Objects.equals(processName, that.processName) &&
                Objects.equals(mainBo, that.mainBo) &&
                Objects.equals(creatorRenid, that.creatorRenid) &&
                Objects.equals(createTimestamp, that.createTimestamp) &&
                Objects.equals(lastLaunchTimestamp, that.lastLaunchTimestamp) &&
                Objects.equals(selfsignature, that.selfsignature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processId, processName, mainBo, creatorRenid, createTimestamp, launchCount, successCount, lastLaunchTimestamp, averageCost, state, authtype, selfsignature);
    }
}
