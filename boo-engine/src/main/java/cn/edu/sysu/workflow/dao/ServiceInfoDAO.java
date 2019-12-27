package cn.edu.sysu.workflow.dao;

import cn.edu.sysu.workflow.entity.ServiceInfo;

/**
 * 服务信息Dao
 *
 * @author Skye
 * Created on 2019/12/26
 */
public interface ServiceInfoDAO {

    ServiceInfo saveOrUpdate(ServiceInfo serviceInfo);

    ServiceInfo findByServiceInfoId(String serviceInfoId);

    ServiceInfo deleteByServiceInfoId(String serviceInfoId);

}
