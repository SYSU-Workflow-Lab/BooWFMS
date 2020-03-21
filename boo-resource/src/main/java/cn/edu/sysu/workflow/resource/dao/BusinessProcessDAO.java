package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.BusinessProcess;

/**
 * 业务流程数据库操作
 *
 * @author Skye
 * Created on 2020/1/1
 */
public interface BusinessProcessDAO {

    /**
     * 更新业务流程，不允许更新业务流程Id
     *
     * @param businessProcess <ul>
     *                        <li>businessProcessId</li>
     *                        <li>businessProcessName</li>
     *                        <li>mainBusinessObjectName</li>
     *                        <li>creatorId</li>
     *                        <li>launchCount</li>
     *                        <li>successCount</li>
     *                        <li>averageCost</li>
     *                        <li>status</li>
     *                        <li>lastLaunchTimestamp</li>
     *                        </ul>
     * @return 修改的数据量
     */
    int update(BusinessProcess businessProcess);

    /**
     * 根据业务流程Id查找业务流程
     *
     * @param businessProcessId
     * @return
     */
    BusinessProcess findOne(String businessProcessId);

}
