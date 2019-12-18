package cn.edu.sysu.workflow.entity.access;

/**
 * Association of Role and Account for BooWFMS
 *
 * Created by Skye on 2019/12/18.
 */
public class RoleAccount {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 对应账户ID
     */
    private String accountId;

    public RoleAccount() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
