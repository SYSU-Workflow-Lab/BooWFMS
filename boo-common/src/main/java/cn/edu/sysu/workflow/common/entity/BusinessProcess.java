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
public class BusinessProcess extends BooPagedQuery {

    private static final long serialVersionUID = 6470788893684196731L;
    public static final String PREFIX = "bp-";

    /**
     * 业务流程ID
     */
    private String businessProcessId;

    /**
     * 业务流程名称
     */
    private String businessProcessName;

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


    public BusinessProcess() {
        super();
    }

    public String getBusinessProcessId() {
        return businessProcessId;
    }

    public void setBusinessProcessId(String businessProcessId) {
        this.businessProcessId = businessProcessId;
    }

    public String getBusinessProcessName() {
        return businessProcessName;
    }

    public void setBusinessProcessName(String businessProcessName) {
        this.businessProcessName = businessProcessName;
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
        BusinessProcess that = (BusinessProcess) o;
        return Objects.equals(businessProcessId, that.businessProcessId) &&
                Objects.equals(businessProcessName, that.businessProcessName) &&
                Objects.equals(mainBusinessObjectId, that.mainBusinessObjectId) &&
                Objects.equals(creatorId, that.creatorId) &&
                Objects.equals(launchCount, that.launchCount) &&
                Objects.equals(successCount, that.successCount) &&
                Objects.equals(averageCost, that.averageCost) &&
                Objects.equals(status, that.status) &&
                Objects.equals(lastLaunchTimestamp, that.lastLaunchTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessProcessId, businessProcessName, mainBusinessObjectId, creatorId, launchCount, successCount, averageCost, status, lastLaunchTimestamp);
    }
}
