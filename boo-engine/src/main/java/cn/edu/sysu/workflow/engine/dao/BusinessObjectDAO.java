package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BusinessObject;

import java.util.List;

/**
 * 业务对象数据库处理
 *
 * @author Skye
 * Created on 2019/12/30
 */
public interface BusinessObjectDAO {

    /**
     * 更新业务对象，不允许更新业务对象Id
     *
     * @param businessObject <ul>
     *                <li>businessObjectId</li>
     *                <li>businessObjectName</li>
     *                <li>processId</li>
     *                <li>status</li>
     *                <li>content</li>
     *                <li>serialization</li>
     *                <li>businessRoles</li>
     *                </ul>
     * @return 修改的数据量
     */
    int update(BusinessObject businessObject);

    /**
     * 根据流程Id查找业务对象列表
     *
     * @param processId
     * @return
     */
    List<BusinessObject> findBusinessObjectsByProcessId(String processId);

}
