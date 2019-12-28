package cn.edu.sysu.workflow.dao;

import cn.edu.sysu.workflow.entity.ServiceInfo;

/**
 * 服务信息Dao
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
     * 更新服务信息，不允许更新serviceInfoId
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
     * 根据serviceInfoId查找服务信息
     *
     * @param serviceInfoId
     * @return
     */
    ServiceInfo findByServiceInfoId(String serviceInfoId);

    /**
     * 根据serviceInfoId删除服务信息
     *
     * @param serviceInfoId
     * @return
     */
    int deleteByServiceInfoId(String serviceInfoId);

}
