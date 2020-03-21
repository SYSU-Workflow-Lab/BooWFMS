package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.BusinessObject;

/**
 * 业务对象数据库操作
 *
 * @author Skye
 * Created on 2019/12/30
 */
public interface BusinessObjectDAO {

    /**
     * 根据业务对象Id查找业务对象数据
     *
     * @param businessObjectId
     * @return
     */
    BusinessObject findOne(String businessObjectId);

}
