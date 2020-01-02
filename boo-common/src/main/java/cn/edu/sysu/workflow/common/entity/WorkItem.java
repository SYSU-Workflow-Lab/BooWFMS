package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.common.enums.WorkItemResourcingStatus;
import cn.edu.sysu.workflow.common.enums.WorkItemStatus;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * WorkItem of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class WorkItem extends BooPagedQuery {

    private static final long serialVersionUID = 3955119742711773965L;
    public static final String PREFIX = "wi-";

    /**
     * 工作项ID
     */
    private String workItemId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 资源服务ID
     */
    private String resourceServiceId;

    /**
     * 所属流程ID
     */
    private String processId;

    /**
     * 业务对象ID
     */
    private String businessObjectId;

    /**
     * 来源任务ID
     */
    private String taskId;

    /**
     * 来源任务的模型ID
     */
    private String taskPolymorphismId;

    /**
     * 相关参数
     */
    private String arguments;

    /**
     * 分配时间戳
     */
    private Timestamp allocateTimestamp;

    /**
     * 开始时间戳
     */
    private Timestamp launchTimestamp;

    /**
     * 完成时间戳
     */
    private Timestamp finishTimestamp;

    /**
     * 工作项状态
     * @see WorkItemStatus
     */
    private String status;

    /**
     * 工作项资源调度生命周期状态
     * @see WorkItemResourcingStatus
     */
    private String resourcingStatus;

    /**
     * 工作项启动者ID
     */
    private String launchAccountId;

    /**
     * 工作项完成者ID
     */
    private String finishAccountId;

    /**
     * TODO 启动时间触发器标识符
     */
    private String timerTriggerId;

    /**
     * TODO 中止时间触发器标识符
     */
    private String timerExpiryId;

    /**
     * 最后一次启动时间戳
     */
    private Timestamp lastLaunchTime;

    /**
     * TODO 执行时间间隔（ms）
     */
    private Long executeTime;

    /**
     * 回调事件发送目的地业务对象的标识符
     */
    private String callbackNodeId;

    public WorkItem() {
        super();
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

    public String getResourceServiceId() {
        return resourceServiceId;
    }

    public void setResourceServiceId(String resourceServiceId) {
        this.resourceServiceId = resourceServiceId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getBusinessObjectId() {
        return businessObjectId;
    }

    public void setBusinessObjectId(String businessObjectId) {
        this.businessObjectId = businessObjectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Timestamp getAllocateTimestamp() {
        return allocateTimestamp;
    }

    public void setAllocateTimestamp(Timestamp allocateTimestamp) {
        this.allocateTimestamp = allocateTimestamp;
    }

    public Timestamp getLaunchTimestamp() {
        return launchTimestamp;
    }

    public void setLaunchTimestamp(Timestamp launchTimestamp) {
        this.launchTimestamp = launchTimestamp;
    }

    public Timestamp getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(Timestamp finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResourcingStatus() {
        return resourcingStatus;
    }

    public void setResourcingStatus(String resourcingStatus) {
        this.resourcingStatus = resourcingStatus;
    }

    public String getLaunchAccountId() {
        return launchAccountId;
    }

    public void setLaunchAccountId(String launchAccountId) {
        this.launchAccountId = launchAccountId;
    }

    public String getFinishAccountId() {
        return finishAccountId;
    }

    public void setFinishAccountId(String finishAccountId) {
        this.finishAccountId = finishAccountId;
    }

    public String getTimerTriggerId() {
        return timerTriggerId;
    }

    public void setTimerTriggerId(String timerTriggerId) {
        this.timerTriggerId = timerTriggerId;
    }

    public String getTimerExpiryId() {
        return timerExpiryId;
    }

    public void setTimerExpiryId(String timerExpiryId) {
        this.timerExpiryId = timerExpiryId;
    }

    public Timestamp getLastLaunchTime() {
        return lastLaunchTime;
    }

    public void setLastLaunchTime(Timestamp lastLaunchTime) {
        this.lastLaunchTime = lastLaunchTime;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkItem workItem = (WorkItem) o;
        return Objects.equals(workItemId, workItem.workItemId) &&
                Objects.equals(processInstanceId, workItem.processInstanceId) &&
                Objects.equals(resourceServiceId, workItem.resourceServiceId) &&
                Objects.equals(processId, workItem.processId) &&
                Objects.equals(businessObjectId, workItem.businessObjectId) &&
                Objects.equals(taskId, workItem.taskId) &&
                Objects.equals(taskPolymorphismId, workItem.taskPolymorphismId) &&
                Objects.equals(arguments, workItem.arguments) &&
                Objects.equals(allocateTimestamp, workItem.allocateTimestamp) &&
                Objects.equals(launchTimestamp, workItem.launchTimestamp) &&
                Objects.equals(finishTimestamp, workItem.finishTimestamp) &&
                Objects.equals(status, workItem.status) &&
                Objects.equals(resourcingStatus, workItem.resourcingStatus) &&
                Objects.equals(launchAccountId, workItem.launchAccountId) &&
                Objects.equals(finishAccountId, workItem.finishAccountId) &&
                Objects.equals(timerTriggerId, workItem.timerTriggerId) &&
                Objects.equals(timerExpiryId, workItem.timerExpiryId) &&
                Objects.equals(lastLaunchTime, workItem.lastLaunchTime) &&
                Objects.equals(executeTime, workItem.executeTime) &&
                Objects.equals(callbackNodeId, workItem.callbackNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workItemId, processInstanceId, resourceServiceId, processId, businessObjectId, taskId,
                taskPolymorphismId, arguments, allocateTimestamp, launchTimestamp, finishTimestamp,
                status, resourcingStatus, launchAccountId, finishAccountId, timerTriggerId, timerExpiryId,
                lastLaunchTime, executeTime, callbackNodeId);
    }
}
