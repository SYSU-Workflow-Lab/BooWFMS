package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooRsTask extends BooPagedQuery {

    private static final long serialVersionUID = 982206185945701947L;

    private String rsTaskId;
    private String boId;
    private String polymorphismName;
    private String polymorphismId;
    private String brole;
    private String principle;
    private String eventdescriptor;
    private String hookdescriptor;
    private String documentation;
    private String parameters;

    public BooRsTask() {
    }

    public String getRsTaskId() {
        return rsTaskId;
    }

    public void setRsTaskId(String rsTaskId) {
        this.rsTaskId = rsTaskId;
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getPolymorphismName() {
        return polymorphismName;
    }

    public void setPolymorphismName(String polymorphismName) {
        this.polymorphismName = polymorphismName;
    }

    public String getPolymorphismId() {
        return polymorphismId;
    }

    public void setPolymorphismId(String polymorphismId) {
        this.polymorphismId = polymorphismId;
    }

    public String getBrole() {
        return brole;
    }

    public void setBrole(String brole) {
        this.brole = brole;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getEventdescriptor() {
        return eventdescriptor;
    }

    public void setEventdescriptor(String eventdescriptor) {
        this.eventdescriptor = eventdescriptor;
    }

    public String getHookdescriptor() {
        return hookdescriptor;
    }

    public void setHookdescriptor(String hookdescriptor) {
        this.hookdescriptor = hookdescriptor;
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
        BooRsTask booRsTask = (BooRsTask) o;
        return Objects.equals(rsTaskId, booRsTask.rsTaskId) &&
                Objects.equals(boId, booRsTask.boId) &&
                Objects.equals(polymorphismName, booRsTask.polymorphismName) &&
                Objects.equals(polymorphismId, booRsTask.polymorphismId) &&
                Objects.equals(brole, booRsTask.brole) &&
                Objects.equals(principle, booRsTask.principle) &&
                Objects.equals(eventdescriptor, booRsTask.eventdescriptor) &&
                Objects.equals(hookdescriptor, booRsTask.hookdescriptor) &&
                Objects.equals(documentation, booRsTask.documentation) &&
                Objects.equals(parameters, booRsTask.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rsTaskId, boId, polymorphismName, polymorphismId, brole, principle, eventdescriptor, hookdescriptor, documentation, parameters);
    }
}
