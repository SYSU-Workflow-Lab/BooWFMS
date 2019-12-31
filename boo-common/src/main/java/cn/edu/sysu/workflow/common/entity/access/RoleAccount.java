package cn.edu.sysu.workflow.common.entity.access;

/**
 * Association of Role and Account of BooWFMS.
 *
 * @author Skye
 * Created on 2019/12/18
 */
public class RoleAccount {

    public static final String PREFIX = "ra-";

    /**
     * 角色账号关联表主键
     */
    private String roleAccountId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 对应账户ID
     */
    private String accountId;

    public RoleAccount() {
        super();
    }

    public String getRoleAccountId() {
        return roleAccountId;
    }

    public void setRoleAccountId(String roleAccountId) {
        this.roleAccountId = roleAccountId;
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
