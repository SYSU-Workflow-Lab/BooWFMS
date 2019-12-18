package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class BooBO extends BooPagedQuery {

    private static final long serialVersionUID = -3590428535722303895L;

    private String boId;
    private String boName;
    private String pid;
    private int state;
    private String content;
    private byte[] serialized;
    private String broles;

    public BooBO() {
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getBoName() {
        return boName;
    }

    public void setBoName(String boName) {
        this.boName = boName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBoContent() {
        return boContent;
    }

    public void setBoContent(String boContent) {
        this.boContent = boContent;
    }

    public byte[] getSerialized() {
        return serialized;
    }

    public void setSerialized(byte[] serialized) {
        this.serialized = serialized;
    }

    public String getBroles() {
        return broles;
    }

    public void setBroles(String broles) {
        this.broles = broles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooBO booBO = (BooBO) o;
        return state == booBO.state &&
                Objects.equals(boId, booBO.boId) &&
                Objects.equals(boName, booBO.boName) &&
                Objects.equals(pid, booBO.pid) &&
                Objects.equals(boContent, booBO.boContent) &&
                Arrays.equals(serialized, booBO.serialized) &&
                Objects.equals(broles, booBO.broles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(boId, boName, pid, state, boContent, broles);
        result = 31 * result + Arrays.hashCode(serialized);
        return result;
    }
}
