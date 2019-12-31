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
     * 根据流程Id查找业务对象列表
     *
     * @param processId
     * @return
     */
    List<BusinessObject> findBusinessObjectsByProcessId(String processId);

}
