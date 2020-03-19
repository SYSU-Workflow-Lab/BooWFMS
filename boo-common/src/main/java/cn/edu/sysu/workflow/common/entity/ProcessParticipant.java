package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.common.enums.AgentReentrantType;
import cn.edu.sysu.workflow.common.enums.ProcessParticipantType;

import java.util.Objects;

/**
 * BusinessProcess Participant of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/24
 */
public class ProcessParticipant extends BooPagedQuery {

    private static final long serialVersionUID = 5391273151954075948L;
    public static final String PREFIX = "pp-";

    /**
     * 流程参与者ID
     */
    private String processParticipantId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 类型
     * @see ProcessParticipantType
     */
    private Integer type;

    /**
     * URL位置，当类型为Agent时用
     */
    private String agentLocation;

    /**
     * 重入类型，当类型为Agent时用
     * @see AgentReentrantType
     */
    private Integer reentrantType;

    /**
     * 备注信息
     */
    private String note;

    public ProcessParticipant() {
        super();
    }

    public String getProcessParticipantId() {
        return processParticipantId;
    }

    public void setProcessParticipantId(String processParticipantId) {
        this.processParticipantId = processParticipantId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAgentLocation() {
        return agentLocation;
    }

    public void setAgentLocation(String agentLocation) {
        this.agentLocation = agentLocation;
    }

    public Integer getReentrantType() {
        return reentrantType;
    }

    public void setReentrantType(Integer reentrantType) {
        this.reentrantType = reentrantType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessParticipant that = (ProcessParticipant) o;
        return Objects.equals(processParticipantId, that.processParticipantId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(type, that.type) &&
                Objects.equals(agentLocation, that.agentLocation) &&
                Objects.equals(reentrantType, that.reentrantType) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processParticipantId, accountId, displayName, type, agentLocation, reentrantType, note);
    }
}
