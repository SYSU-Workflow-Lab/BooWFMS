package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.common.entity.BusinessRoleMap;

import java.util.List;

/**
 * 业务角色映射数据库操作
 *
 * @author Skye
 * Created on 2020/4/27
 */
public interface BusinessRoleMapDAO {

    /**
     * 创建服务信息
     *
     * @param businessRoleMap <ul>
     *                        <li>businessRoleMapId</li>
     *                        <li>processInstanceId</li>
     *                        <li>businessRoleName</li>
     *                        <li>organizationId</li>
     *                        <li>mappedAccountId</li>
     *                        <li>dataVersion</li>
     *                        </ul>
     * @return 新增的数据量
     */
    int save(BusinessRoleMap businessRoleMap);

    /**
     * 根据业务流程实例ID查找相关业务角色列表
     *
     * @param processInstanceId
     * @return
     */
    List<BusinessRoleMap> findBusinessRoleMapsByProcessInstanceId(String processInstanceId);

}
