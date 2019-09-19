package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Created by Skye on 2019/9/19.
 */
public class BooRsParticipant extends BooPagedQuery {

    private static final long serialVersionUID = 5391273151954075948L;

    private String rsParticipantId;
    private String workerid;
    private String displayname;
    private int type;
    private int reentrantType;
    private String agentLocation;
    private String note;

    public BooRsParticipant() {
    }

    public String getRsParticipantId() {
        return rsParticipantId;
    }

    public void setRsParticipantId(String rsParticipantId) {
        this.rsParticipantId = rsParticipantId;
    }

    public String getWorkerid() {
        return workerid;
    }

    public void setWorkerid(String workerid) {
        this.workerid = workerid;
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

    public int getReentrantType() {
        return reentrantType;
    }

    public void setReentrantType(int reentrantType) {
        this.reentrantType = reentrantType;
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
        BooRsParticipant that = (BooRsParticipant) o;
        return type == that.type &&
                reentrantType == that.reentrantType &&
                Objects.equals(rsParticipantId, that.rsParticipantId) &&
                Objects.equals(workerid, that.workerid) &&
                Objects.equals(displayname, that.displayname) &&
                Objects.equals(agentLocation, that.agentLocation) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rsParticipantId, workerid, displayname, type, reentrantType, agentLocation, note);
    }
}
