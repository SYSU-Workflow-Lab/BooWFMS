package cn.edu.sysu.workflow.common.entity.human;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * 用户职能
 *
 * @author Skye
 * Created on 2020/5/12
 */
public class Capability extends BooPagedQuery {

    private static final long serialVersionUID = 3465599574507372082L;
    private static final String PREFIX = "capability-";

    /**
     * 职能ID
     */
    private String capabilityId;

    /**
     * 职能名称
     */
    private String name;

    /**
     * 职能描述
     */
    private String description;

    public Capability() {
        super();
    }

    public String getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(String capabilityId) {
        this.capabilityId = capabilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Capability that = (Capability) o;
        return Objects.equals(capabilityId, that.capabilityId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capabilityId, name, description);
    }
}
