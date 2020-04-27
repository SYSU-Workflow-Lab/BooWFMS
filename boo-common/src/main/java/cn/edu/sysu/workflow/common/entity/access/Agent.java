package cn.edu.sysu.workflow.common.entity.access;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.common.enums.AgentReentrantType;

import java.util.Objects;

/**
 * Agent of BooWFMS.
 *
 * @author Skye
 * Created on 2020/4/27
 */
public class Agent extends BooPagedQuery {

    private static final long serialVersionUID = -8959656057357308949L;
    public static final String PREFIX = "agent-";

    /**
     * agent ID
     */
    private String agentId;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * URL位置
     */
    private String location;

    /**
     * @see AgentReentrantType
     */
    private Integer reentrantType;

    public Agent() {
        super();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getReentrantType() {
        return reentrantType;
    }

    public void setReentrantType(Integer reentrantType) {
        this.reentrantType = reentrantType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return Objects.equals(agentId, agent.agentId) &&
                Objects.equals(displayName, agent.displayName) &&
                Objects.equals(location, agent.location) &&
                Objects.equals(reentrantType, agent.reentrantType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agentId, displayName, location, reentrantType);
    }
}
