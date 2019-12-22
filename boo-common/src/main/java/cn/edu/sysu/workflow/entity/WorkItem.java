package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.utils.IdUtil;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * WorkItem of BooWFMS
 *
 * Created by Skye on 2019/12/18.
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
    private String resourceId;

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
     * 来源任务的模型 ID
     */
    private String taskPolymorphismId;

    /**
     * 相关参数
     */
    private String arguments;

    /**
     * 创建时间戳
     */
    private Timestamp createTimestamp;

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
     * TODO 资源服务状态
     */
    private String status;

    /**
     * TODO 资源生命周期状态
     */
    private String resourceStatus;

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
    private long executeTime;

    /**
     * 回调事件发送目的地业务对象的标识符
     */
    private String callbackNodeId;

    public WorkItem() {
        this.workItemId = PREFIX + IdUtil.nextId();
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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
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

    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
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
        WorkItem workItem = (WorkItem) o;
        return executeTime == workItem.executeTime &&
                Objects.equals(workItemId, workItem.workItemId) &&
                Objects.equals(processInstanceId, workItem.processInstanceId) &&
                Objects.equals(resourceId, workItem.resourceId) &&
                Objects.equals(processId, workItem.processId) &&
                Objects.equals(businessObjectId, workItem.businessObjectId) &&
                Objects.equals(taskId, workItem.taskId) &&
                Objects.equals(taskPolymorphismId, workItem.taskPolymorphismId) &&
                Objects.equals(arguments, workItem.arguments) &&
                Objects.equals(createTimestamp, workItem.createTimestamp) &&
                Objects.equals(allocateTimestamp, workItem.allocateTimestamp) &&
                Objects.equals(launchTimestamp, workItem.launchTimestamp) &&
                Objects.equals(finishTimestamp, workItem.finishTimestamp) &&
                Objects.equals(status, workItem.status) &&
                Objects.equals(resourceStatus, workItem.resourceStatus) &&
                Objects.equals(launchAccountId, workItem.launchAccountId) &&
                Objects.equals(finishAccountId, workItem.finishAccountId) &&
                Objects.equals(timerTriggerId, workItem.timerTriggerId) &&
                Objects.equals(timerExpiryId, workItem.timerExpiryId) &&
                Objects.equals(lastLaunchTime, workItem.lastLaunchTime) &&
                Objects.equals(callbackNodeId, workItem.callbackNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workItemId, processInstanceId, resourceId, processId, businessObjectId, taskId, taskPolymorphismId, arguments, createTimestamp, allocateTimestamp, launchTimestamp, finishTimestamp, status, resourceStatus, launchAccountId, finishAccountId, timerTriggerId, timerExpiryId, lastLaunchTime, executeTime, callbackNodeId);
    }
}
