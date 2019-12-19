package cn.edu.sysu.workflow.entity.access;

import java.util.UUID;

/**
 * Association of Role and Organization of BooWFMS
 *
 * Created by Skye on 2019/12/18.
 */
public class RoleOrganization {

    private final String PREFIX = "ro-";

    /**
     * 角色账号关联表主键
     */
    private String roleOrganizationId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 对应组织ID
     */
    private String organizationId;

    public RoleOrganization() {
        this.roleOrganizationId = PREFIX + UUID.randomUUID().toString();
    }

    public String getPREFIX() {
        return PREFIX;
    }

    public String getRoleOrganizationId() {
        return roleOrganizationId;
    }

    public void setRoleOrganizationId(String roleOrganizationId) {
        this.roleOrganizationId = roleOrganizationId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
