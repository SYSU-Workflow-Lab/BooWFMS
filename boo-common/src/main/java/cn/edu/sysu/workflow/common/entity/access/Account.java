package cn.edu.sysu.workflow.common.entity.access;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Account of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class Account extends BooPagedQuery {

    private static final long serialVersionUID = 1518574941723263652L;
    public static final String PREFIX = "account-";

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 账户名
     */
    private String username;

    /**
     * 账户密码(加盐做摘要）
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 账户状态（0-停用，1-正常）
     */
    private Integer status;

    /**
     * 账户级别
     * {@link cn.edu.sysu.workflow.common.enums.AccountLevel}
     */
    private Integer level;

    /**
     * 最后登录时间
     */
    private Timestamp lastLoginTimestamp;

    public Account() {
        super();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Timestamp getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Timestamp lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(salt, account.salt) &&
                Objects.equals(organizationName, account.organizationName) &&
                Objects.equals(status, account.status) &&
                Objects.equals(level, account.level) &&
                Objects.equals(lastLoginTimestamp, account.lastLoginTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, username, password, salt, organizationName, status, level, lastLoginTimestamp);
    }
}
