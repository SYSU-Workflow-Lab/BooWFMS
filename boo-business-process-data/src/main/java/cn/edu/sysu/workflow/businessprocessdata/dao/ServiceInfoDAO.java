package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.common.entity.ServiceInfo;

/**
 * 服务信息数据库操作
 *
 * @author Skye
 * Created on 2019/12/26
 */
public interface ServiceInfoDAO {

    /**
     * 创建服务信息
     *
     * @param serviceInfo <ul>
     *                    <li>serviceInfoId</li>
     *                    <li>url</li>
     *                    <li>isActive</li>
     *                    <li>business</li>
     *                    <li>cpuOccupancyRate</li>
     *                    <li>memoryOccupancyRate</li>
     *                    <li>tomcatConcurrency</li>
     *                    <li>workItemCount</li>
     *                    </ul>
     * @return 新增的数据量
     */
    int save(ServiceInfo serviceInfo);

    /**
     * 更新服务信息，不允许更新服务信息Id
     *
     * @param serviceInfo <ul>
     *                    <li>serviceInfoId</li>
     *                    <li>url</li>
     *                    <li>isActive</li>
     *                    <li>business</li>
     *                    <li>cpuOccupancyRate</li>
     *                    <li>memoryOccupancyRate</li>
     *                    <li>tomcatConcurrency</li>
     *                    <li>workItemCount</li>
     *                    </ul>
     * @return 修改的数据量
     */
    int update(ServiceInfo serviceInfo);

    /**
     * 根据服务信息Id查找服务信息
     *
     * @param serviceInfoId
     * @return
     */
    ServiceInfo findOne(String serviceInfoId);

    /**
     * 根据服务信息Id删除服务信息
     *
     * @param serviceInfoId
     * @return
     */
    int deleteByServiceInfoId(String serviceInfoId);

    /**
     * 根据流程实例ID查找对应的资源服务URL
     *
     * @param processInstanceId
     * @return
     */
    String findResourceServiceUrlByProcessInstanceId(String processInstanceId);

}
