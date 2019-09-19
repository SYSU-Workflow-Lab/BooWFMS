package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Skye on 2019/9/18.
 */
public class BooBinStep extends BooPagedQuery {

    private static final long serialVersionUID = 2776125695679340149L;

    private String binStepId;
    private String runtimeRecordId;
    private String supervisorId;
    private String notifiableId;
    private byte[] binlog;

    public String getBinStepId() {
        return binStepId;
    }

    public void setBinStepId(String binStepId) {
        this.binStepId = binStepId;
    }

    public String getRuntimeRecordId() {
        return runtimeRecordId;
    }

    public void setRuntimeRecordId(String runtimeRecordId) {
        this.runtimeRecordId = runtimeRecordId;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getNotifiableId() {
        return notifiableId;
    }

    public void setNotifiableId(String notifiableId) {
        this.notifiableId = notifiableId;
    }

    public byte[] getBinlog() {
        return binlog;
    }

    public void setBinlog(byte[] binlog) {
        this.binlog = binlog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooBinStep that = (BooBinStep) o;
        return Objects.equals(binStepId, that.binStepId) &&
                Objects.equals(runtimeRecordId, that.runtimeRecordId) &&
                Objects.equals(supervisorId, that.supervisorId) &&
                Objects.equals(notifiableId, that.notifiableId) &&
                Arrays.equals(binlog, that.binlog);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(binStepId, runtimeRecordId, supervisorId, notifiableId);
        result = 31 * result + Arrays.hashCode(binlog);
        return result;
    }
}
