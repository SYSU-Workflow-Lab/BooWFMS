package cn.edu.sysu.workflow.common.entity.human;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * 用户与用户职能多对多映射
 *
 * @author Skye
 * Created on 2020/5/12
 */
public class AccountCapability extends BooPagedQuery {

    private static final long serialVersionUID = -8972383700308936439L;
    private static final String PREFIX = "ac-";

    /**
     * 数据库主键
     */
    private String accountCapabilityId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 职能ID
     */
    private String capabilityId;

    public AccountCapability() {
        super();
    }

    public String getAccountCapabilityId() {
        return accountCapabilityId;
    }

    public void setAccountCapabilityId(String accountCapabilityId) {
        this.accountCapabilityId = accountCapabilityId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(String capabilityId) {
        this.capabilityId = capabilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountCapability that = (AccountCapability) o;
        return Objects.equals(accountCapabilityId, that.accountCapabilityId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(capabilityId, that.capabilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountCapabilityId, accountId, capabilityId);
    }
}
