package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.enums.ProcessParticipantType;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Objects;

/**
 * Process Participant of BooWFMS.
 *
 * Created by Skye on 2019/12/24.
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
    private String displayname;

    /**
     * 类型
     * @see ProcessParticipantType
     */
    private int type;

    /**
     * URL位置，当类型为Agent时用
     */
    private String agentLocation;

    /**
     * 备注信息
     */
    private String note;

    public ProcessParticipant() {
        this.processParticipantId = PREFIX + IdUtil.nextId();
    }

    public String getProcessParticipantId() {
        return processParticipantId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getAgentLocation() {
        return agentLocation;
    }

    public void setAgentLocation(String agentLocation) {
        this.agentLocation = agentLocation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessParticipant that = (ProcessParticipant) o;
        return type == that.type &&
                Objects.equals(processParticipantId, that.processParticipantId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(displayname, that.displayname) &&
                Objects.equals(agentLocation, that.agentLocation) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processParticipantId, accountId, displayname, type, agentLocation, note);
    }
}
