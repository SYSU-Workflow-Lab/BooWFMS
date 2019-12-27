package cn.edu.sysu.workflow.entity;

import cn.edu.sysu.workflow.entity.base.BooPagedQuery;
import cn.edu.sysu.workflow.util.IdUtil;

import java.util.Arrays;
import java.util.Objects;

/**
 * Business Object of BooWFMS.
 *
 * @author Skye
 * Created on 2019/12/18
 */
public class BusinessObject extends BooPagedQuery {

    private static final long serialVersionUID = -3590428535722303895L;
    public static final String PREFIX = "bo-";

    /**
     * 业务对象ID
     */
    private String businessObjectId;

    /**
     * 业务对象名称
     */
    private String businessObjectName;

    /**
     * 所述流程ID
     */
    private String processId;

    /**
     * 业务对象状态（0-停用，1-正常）
     */
    private int status;

    /**
     * 业务对象模型内容
     */
    private String content;

    /**
     * 序列化的业务对象
     */
    private byte[] serialization;

    /**
     * 涉及的业务角色
     */
    private String businessRoles;

    public BusinessObject() {
        super();
    }

    public String getBusinessObjectId() {
        return businessObjectId;
    }

    public void setBusinessObjectId(String businessObjectId) {
        this.businessObjectId = businessObjectId;
    }

    public String getBusinessObjectName() {
        return businessObjectName;
    }

    public void setBusinessObjectName(String businessObjectName) {
        this.businessObjectName = businessObjectName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getSerialization() {
        return serialization;
    }

    public void setSerialization(byte[] serialization) {
        this.serialization = serialization;
    }

    public String getBusinessRoles() {
        return businessRoles;
    }

    public void setBusinessRoles(String businessRoles) {
        this.businessRoles = businessRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessObject that = (BusinessObject) o;
        return status == that.status &&
                Objects.equals(businessObjectId, that.businessObjectId) &&
                Objects.equals(businessObjectName, that.businessObjectName) &&
                Objects.equals(processId, that.processId) &&
                Objects.equals(content, that.content) &&
                Arrays.equals(serialization, that.serialization) &&
                Objects.equals(businessRoles, that.businessRoles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(businessObjectId, businessObjectName, processId, status, content, businessRoles);
        result = 31 * result + Arrays.hashCode(serialization);
        return result;
    }
}
