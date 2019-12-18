package cn.edu.sysu.workflow.entity.access;

/**
 * Association of Role and Organization for BooWFMS
 *
 * Created by Skye on 2019/12/18.
 */
public class RoleOrganization {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 对应组织ID
     */
    private String organizationId;

    public RoleOrganization() {
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
