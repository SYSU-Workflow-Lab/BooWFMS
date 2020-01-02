package cn.edu.sysu.workflow.common.entity;

import cn.edu.sysu.workflow.common.entity.base.BooPagedQuery;

import java.util.Objects;

/**
 * Role Map of BooWFMS.
 *
 * @author Rinkako, Skye
 * Created on 2019/12/24
 */
public class RoleMap extends BooPagedQuery {

    private static final long serialVersionUID = -2698669379061568634L;
    public static final String PREFIX = "rm-";

    /**
     * 数据库主键
     */
    private String roleMapId;

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
     * TODO
     */
    private String mappedId;

    /**
     * 数据版本
     */
    private String dataVersion;

    /**
     * TODO
     */
    private Integer isArchived;

    public RoleMap() {
        super();
    }

    public String getRoleMapId() {
        return roleMapId;
    }

    public void setRoleMapId(String roleMapId) {
        this.roleMapId = roleMapId;
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

    public String getMappedId() {
        return mappedId;
    }

    public void setMappedId(String mappedId) {
        this.mappedId = mappedId;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Integer isArchived) {
        this.isArchived = isArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleMap roleMap = (RoleMap) o;
        return Objects.equals(roleMapId, roleMap.roleMapId) &&
                Objects.equals(processInstanceId, roleMap.processInstanceId) &&
                Objects.equals(businessRoleName, roleMap.businessRoleName) &&
                Objects.equals(organizationId, roleMap.organizationId) &&
                Objects.equals(mappedId, roleMap.mappedId) &&
                Objects.equals(dataVersion, roleMap.dataVersion) &&
                Objects.equals(isArchived, roleMap.isArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleMapId, processInstanceId, businessRoleName, organizationId, mappedId, dataVersion, isArchived);
    }
}
