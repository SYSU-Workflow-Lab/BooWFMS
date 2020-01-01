package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Business Process of BooWFMS.
 *
 * @author Skye
 * Created on 2019/12/18
 */
public class Process extends BooPagedQuery {

    private static final long serialVersionUID = 6470788893684196731L;
    public static final String PREFIX = "process-";

    /**
     * 流程ID
     */
    private String processId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 主业务对象ID
     */
    private String mainBusinessObjectId;

    /**
     * 创建者ID
     */
    private String creatorId;

    /**
     * 启动次数
     */
    private Integer launchCount;

    /**
     * 成功完成次数
     */
    private Integer successCount;

    /**
     * TODO 平均完成时间（ms）
     */
    private Long averageCost;

    /**
     * 流程状态（0-停用，1-正常）
     */
    private Integer status;

    /**
     * 最后一次启动时间戳
     */
    private Timestamp lastLaunchTimestamp;


    public Process() {
        super();
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

    public String getMainBusinessObjectId() {
        return mainBusinessObjectId;
    }

    public void setMainBusinessObjectId(String mainBusinessObjectId) {
        this.mainBusinessObjectId = mainBusinessObjectId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLaunchCount() {
        return launchCount;
    }

    public void setLaunchCount(Integer launchCount) {
        this.launchCount = launchCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Long getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Long averageCost) {
        this.averageCost = averageCost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getLastLaunchTimestamp() {
        return lastLaunchTimestamp;
    }

    public void setLastLaunchTimestamp(Timestamp lastLaunchTimestamp) {
        this.lastLaunchTimestamp = lastLaunchTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Process process = (Process) o;
        return Objects.equals(processId, process.processId) &&
                Objects.equals(processName, process.processName) &&
                Objects.equals(mainBusinessObjectId, process.mainBusinessObjectId) &&
                Objects.equals(creatorId, process.creatorId) &&
                Objects.equals(launchCount, process.launchCount) &&
                Objects.equals(successCount, process.successCount) &&
                Objects.equals(averageCost, process.averageCost) &&
                Objects.equals(status, process.status) &&
                Objects.equals(lastLaunchTimestamp, process.lastLaunchTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processId, processName, mainBusinessObjectId, creatorId, launchCount, successCount, averageCost, status, lastLaunchTimestamp);
    }
}
