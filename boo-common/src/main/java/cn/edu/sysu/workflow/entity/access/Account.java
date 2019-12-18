package cn.edu.sysu.workflow.entity.access;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Account for BooWFMS
 *
 * Created by Skye on 2019/9/18.
 */
public class Account extends BooPagedQuery {

    private static final long serialVersionUID = 1518574941723263652L;
    private final String PREFIX = "account-";

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
     * TODO
     */
    private String gid;

    /**
     * 账户状态
     */
    private int status;

    /**
     * 账户创建时间
     */
    private Timestamp createdTimestamp;

    /**
     * 最后登录时间
     */
    private Timestamp lastLoginTimestamp;

    public Account() {
        this.accountId = PREFIX + UUID.randomUUID().toString();
    }

    public String getPREFIX() {
        return PREFIX;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
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
        return status == account.status &&
                Objects.equals(accountId, account.accountId) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(organizationId, account.organizationId) &&
                Objects.equals(gid, account.gid) &&
                Objects.equals(createdTimestamp, account.createdTimestamp) &&
                Objects.equals(lastLoginTimestamp, account.lastLoginTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, username, password, organizationId, gid, status, createdTimestamp, lastLoginTimestamp);
    }
}
