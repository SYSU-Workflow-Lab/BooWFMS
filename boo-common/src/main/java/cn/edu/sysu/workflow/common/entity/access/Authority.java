package cn.edu.sysu.workflow.common.entity.access;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Authority of BooWFMS.
 *
 * @author Skye
 * Created on 2020/6/10
 */
public class Authority extends BooPagedQuery {

    private static final long serialVersionUID = 6837346832525369293L;
    public static final String PREFIX = "authority-";

    /**
     * 权限ID
     */
    private String authorityId;

    /**
     * 权限类型：C-添加，R-读取，U-修改，D-删除，E-执行，例CRUDE、-R--E
     */
    private String type;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 业务流程实体ID
     */
    private String businessProcessEntityId;

    public Authority() {
        super();
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBusinessProcessEntityId() {
        return businessProcessEntityId;
    }

    public void setBusinessProcessEntityId(String businessProcessEntityId) {
        this.businessProcessEntityId = businessProcessEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Authority authority = (Authority) o;
        return Objects.equals(authorityId, authority.authorityId) &&
                Objects.equals(type, authority.type) &&
                Objects.equals(accountId, authority.accountId) &&
                Objects.equals(businessProcessEntityId, authority.businessProcessEntityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityId, type, accountId, businessProcessEntityId);
    }
}
