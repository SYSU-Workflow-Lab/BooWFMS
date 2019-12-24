package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Objects;

/**
 * Task (deserialize from model) of BooWFMS
 *
 * Created by Skye on 2019/12/24.
 */
public class Task extends BooPagedQuery {

    private static final long serialVersionUID = 982206185945701947L;
    public static final String PREFIX = "task-";

    /**
     * 数据库主键
     */
    private String taskId;

    /**
     * 业务对象ID
     */
    private String businessObjectId;

    /**
     * 来源任务的模型名
     */
    private String taskPolymorphismName;

    /**
     * 来源任务的模型ID
     */
    private String taskPolymorphismId;

    /**
     * 涉及业务角色
     */
    private String businessRole;

    /**
     * TODO
     */
    private String principle;

    /**
     * TODO
     */
    private String eventDescriptor;

    /**
     * TODO
     */
    private String hookDescriptor;

    /**
     * TODO
     */
    private String documentation;

    /**
     * 参数
     */
    private String parameters;

    public Task() {
        this.taskId = PREFIX + IdUtil.nextId();
    }

    public String getTaskId() {
        return taskId;
    }

    public String getBusinessObjectId() {
        return businessObjectId;
    }

    public void setBusinessObjectId(String businessObjectId) {
        this.businessObjectId = businessObjectId;
    }

    public String getTaskPolymorphismName() {
        return taskPolymorphismName;
    }

    public void setTaskPolymorphismName(String taskPolymorphismName) {
        this.taskPolymorphismName = taskPolymorphismName;
    }

    public String getTaskPolymorphismId() {
        return taskPolymorphismId;
    }

    public void setTaskPolymorphismId(String taskPolymorphismId) {
        this.taskPolymorphismId = taskPolymorphismId;
    }

    public String getBusinessRole() {
        return businessRole;
    }

    public void setBusinessRole(String businessRole) {
        this.businessRole = businessRole;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getEventDescriptor() {
        return eventDescriptor;
    }

    public void setEventDescriptor(String eventDescriptor) {
        this.eventDescriptor = eventDescriptor;
    }

    public String getHookDescriptor() {
        return hookDescriptor;
    }

    public void setHookDescriptor(String hookDescriptor) {
        this.hookDescriptor = hookDescriptor;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId) &&
                Objects.equals(businessObjectId, task.businessObjectId) &&
                Objects.equals(taskPolymorphismName, task.taskPolymorphismName) &&
                Objects.equals(taskPolymorphismId, task.taskPolymorphismId) &&
                Objects.equals(businessRole, task.businessRole) &&
                Objects.equals(principle, task.principle) &&
                Objects.equals(eventDescriptor, task.eventDescriptor) &&
                Objects.equals(hookDescriptor, task.hookDescriptor) &&
                Objects.equals(documentation, task.documentation) &&
                Objects.equals(parameters, task.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, businessObjectId, taskPolymorphismName, taskPolymorphismId, businessRole, principle, eventDescriptor, hookDescriptor, documentation, parameters);
    }
}
