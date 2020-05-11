package cn.edu.sysu.workflow.enginefeign.dao;

import cn.edu.sysu.workflow.common.entity.ServiceInfo;

/**
 * 服务信息数据库操作
 *
 * @author Skye
 * Created on 2019/12/26
 */
public interface ServiceInfoDAO {

    /**
     * 根据服务信息Id查找服务信息
     *
     * @param serviceInfoId
     * @return
     */
    ServiceInfo findOne(String serviceInfoId);

}
