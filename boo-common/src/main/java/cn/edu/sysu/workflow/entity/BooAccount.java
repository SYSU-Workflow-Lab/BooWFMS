package cn.edu.sysu.workflow.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Account for BooWFMS
 *
 * Created by Skye on 2019/9/18.
 */
public class BooAccount extends BooPagedQuery {

    private static final long serialVersionUID = 1518574941723263652L;

    private String accountId;
    private String username;
    private String password;
    private String domain;
    private String gid;
    private int level;
    private int status;
    private Timestamp createtimestamp;
    private Timestamp lastlogin;

    public BooAccount() {
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatetimestamp() {
        return createtimestamp;
    }

    public void setCreatetimestamp(Timestamp createtimestamp) {
        this.createtimestamp = createtimestamp;
    }

    public Timestamp getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Timestamp lastlogin) {
        this.lastlogin = lastlogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooAccount that = (BooAccount) o;
        return level == that.level &&
                status == that.status &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(domain, that.domain) &&
                Objects.equals(gid, that.gid) &&
                Objects.equals(createtimestamp, that.createtimestamp) &&
                Objects.equals(lastlogin, that.lastlogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, username, password, domain, gid, level, status, createtimestamp, lastlogin);
    }
}
