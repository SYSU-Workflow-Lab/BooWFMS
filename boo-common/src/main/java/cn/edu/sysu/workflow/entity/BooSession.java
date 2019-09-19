package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooSession extends BooPagedQuery {

    private static final long serialVersionUID = -1711259478502284228L;

    private String token;
    private String username;
    private int level;
    private Timestamp createTimestamp;
    private Timestamp untilTimestamp;
    private Timestamp destroyTimestamp;

    public BooSession() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getUntilTimestamp() {
        return untilTimestamp;
    }

    public void setUntilTimestamp(Timestamp untilTimestamp) {
        this.untilTimestamp = untilTimestamp;
    }

    public Timestamp getDestroyTimestamp() {
        return destroyTimestamp;
    }

    public void setDestroyTimestamp(Timestamp destroyTimestamp) {
        this.destroyTimestamp = destroyTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooSession that = (BooSession) o;
        return level == that.level &&
                Objects.equals(token, that.token) &&
                Objects.equals(username, that.username) &&
                Objects.equals(createTimestamp, that.createTimestamp) &&
                Objects.equals(untilTimestamp, that.untilTimestamp) &&
                Objects.equals(destroyTimestamp, that.destroyTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username, level, createTimestamp, untilTimestamp, destroyTimestamp);
    }
}
