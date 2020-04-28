package cn.edu.sysu.workflow.common.entity.access;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Organization of BooWFMS.
 *
 * @author Skye
 * Created on 2019/12/18
 */
public class Organization extends BooPagedQuery {

    private static final long serialVersionUID = -1176672623536511029L;
    public static final String PREFIX = "organization-";

    /**
     * 组织ID
     */
    private String organizationId;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织状态（0-停用，1-正常）
     */
    private Integer status;

    /**
     * 父组织ID
     */
    private String parentOrganizationId;

    public Organization() {
        super();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentOrganizationId() {
        return parentOrganizationId;
    }

    public void setParentOrganizationId(String parentOrganizationId) {
        this.parentOrganizationId = parentOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organization that = (Organization) o;
        return Objects.equals(organizationId, that.organizationId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(status, that.status) &&
                Objects.equals(parentOrganizationId, that.parentOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, name, status, parentOrganizationId);
    }
}
