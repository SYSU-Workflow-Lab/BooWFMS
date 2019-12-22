package cn.edu.sysu.workflow.entity.access;

import cn.edu.sysu.workflow.utils.IdUtil;

import java.util.UUID;

/**
 * Association of Role and Organization of BooWFMS
 *
 * Created by Skye on 2019/12/18.
 */
public class RoleOrganization {

    public static final String PREFIX = "ro-";

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
        this.roleOrganizationId = PREFIX + IdUtil.nextId();
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
