package cn.edu.sysu.workflow.entity.access;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.util.IdUtil;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Account of BooWFMS
 *
 * Created by Skye on 2019/12/18.
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
     * 账户密码
     */
    private String password;

    /**
     * 组织ID
     */
    private String organizationId;

    /**
     * 账户状态（0-停用，1-正常）
     */
    private Integer status;

    /**
     * 账户创建时间
     */
    private Timestamp createTimestamp;

    /**
     * 最后登录时间
     */
    private Timestamp lastLoginTimestamp;

    public Account() {
        this.accountId = PREFIX + IdUtil.nextId();
    }

    public String getAccountId() {
        return accountId;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatedTimestamp() {
        return createTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createTimestamp = createdTimestamp;
    }

    public Timestamp getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Timestamp lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(organizationId, account.organizationId) &&
                Objects.equals(status, account.status) &&
                Objects.equals(createTimestamp, account.createTimestamp) &&
                Objects.equals(lastLoginTimestamp, account.lastLoginTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, username, password, organizationId, status, createTimestamp, lastLoginTimestamp);
    }
}
