package cn.edu.sysu.workflow.entity.access;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Organization for BooWFMS
 *
 * Created by Skye on 2019/9/18.
 */
public class Organization extends BooPagedQuery {

    private static final long serialVersionUID = -1176672623536511029L;
    private final String PREFIX = "organization-";

    /**
     * 组织ID
     */
    private String organizationId;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织状态
     */
    private int status;

    /**
     * 父组织ID
     */
    private String parentOrganizationId;

    /**
     * 账户创建时间
     */
    private Timestamp createdTimestamp;

    // TODO 第三方账户管理体系网关
    // private String organGateway;

    // TODO 第三方账户管理体系安全签名
    // private String urlsafeSignature;

    public Organization() {
        this.organizationId = PREFIX + UUID.randomUUID().toString();
        this.parentOrganizationId = "";
    }

    public String getPREFIX() {
        return PREFIX;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParentOrganizationId() {
        return parentOrganizationId;
    }

    public void setParentOrganizationId(String parentOrganizationId) {
        this.parentOrganizationId = parentOrganizationId;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return status == that.status &&
                Objects.equals(organizationId, that.organizationId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(parentOrganizationId, that.parentOrganizationId) &&
                Objects.equals(createdTimestamp, that.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, name, status, parentOrganizationId, createdTimestamp);
    }
}
