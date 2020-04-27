package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Role Map of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/24
 */
public class BusinessRoleMap extends BooPagedQuery {

    private static final long serialVersionUID = -2698669379061568634L;
    public static final String PREFIX = "brm-";

    /**
     * 数据库主键
     */
    private String businessRoleMapId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 业务角色名称
     */
    private String businessRoleName;

    /**
     * TODO 所属组织ID
     */
    private String organizationId;

    /**
     * 映射用户账户ID
     */
    private String mappedAccountId;

    /**
     * 所属组织数据版本
     */
    private String dataVersion;

    public BusinessRoleMap() {
        super();
    }

    public String getBusinessRoleMapId() {
        return businessRoleMapId;
    }

    public void setBusinessRoleMapId(String businessRoleMapId) {
        this.businessRoleMapId = businessRoleMapId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBusinessRoleName() {
        return businessRoleName;
    }

    public void setBusinessRoleName(String businessRoleName) {
        this.businessRoleName = businessRoleName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getMappedAccountId() {
        return mappedAccountId;
    }

    public void setMappedAccountId(String mappedAccountId) {
        this.mappedAccountId = mappedAccountId;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessRoleMap businessRoleMap = (BusinessRoleMap) o;
        return Objects.equals(businessRoleMapId, businessRoleMap.businessRoleMapId) &&
                Objects.equals(processInstanceId, businessRoleMap.processInstanceId) &&
                Objects.equals(businessRoleName, businessRoleMap.businessRoleName) &&
                Objects.equals(organizationId, businessRoleMap.organizationId) &&
                Objects.equals(mappedAccountId, businessRoleMap.mappedAccountId) &&
                Objects.equals(dataVersion, businessRoleMap.dataVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessRoleMapId, processInstanceId, businessRoleName, organizationId, mappedAccountId, dataVersion);
    }
}
