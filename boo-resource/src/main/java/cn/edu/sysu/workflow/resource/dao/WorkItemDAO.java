package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.WorkItem;

import java.util.List;

/**
 * WorkItem数据库操作
 *
 * @author Skye
 * Created on 2020/3/17
 */
public interface WorkItemDAO {

    /**
     * 创建工作项
     *
     * @param workItem <ul>
     *                 <li>workItemId</li>
     *                 <li>processInstanceId</li>
     *                 <li>resourceServiceId</li>
     *                 <li>processId</li>
     *                 <li>businessObjectId</li>
     *                 <li>taskId</li>
     *                 <li>taskPolymorphismId</li>
     *                 <li>arguments</li>
     *                 <li>allocateTimestamp</li>
     *                 <li>launchTimestamp</li>
     *                 <li>finishTimestamp</li>
     *                 <li>status</li>
     *                 <li>resourcingStatus</li>
     *                 <li>launchAccountId</li>
     *                 <li>finishAccountId</li>
     *                 <li>timerTriggerId</li>
     *                 <li>timerExpiryId</li>
     *                 <li>lastLaunchTimestamp</li>
     *                 <li>executeTime</li>
     *                 <li>callbackNodeId</li>
     *                 </ul>
     * @return 新增的数据量
     */
    int save(WorkItem workItem);

    /**
     * 更新工作项，不允许更新工作项Id
     *
     * @param workItem <ul>
     *                 <li>workItemId</li>
     *                 <li>processInstanceId</li>
     *                 <li>resourceServiceId</li>
     *                 <li>processId</li>
     *                 <li>businessObjectId</li>
     *                 <li>taskId</li>
     *                 <li>taskPolymorphismId</li>
     *                 <li>arguments</li>
     *                 <li>allocateTimestamp</li>
     *                 <li>launchTimestamp</li>
     *                 <li>finishTimestamp</li>
     *                 <li>status</li>
     *                 <li>resourcingStatus</li>
     *                 <li>launchAccountId</li>
     *                 <li>finishAccountId</li>
     *                 <li>timerTriggerId</li>
     *                 <li>timerExpiryId</li>
     *                 <li>lastLaunchTimestamp</li>
     *                 <li>executeTime</li>
     *                 <li>callbackNodeId</li>
     *                 </ul>
     * @return 新增的数据量
     */
    int update(WorkItem workItem);

    /**
     * 跟据工作项Id查找工作项对应的工作项数据
     *
     * @param workItemId
     * @return
     */
    WorkItem findOne(String workItemId);

    /**
     * 根据工作项Id查找工作项对应的业务流程实例ID
     *
     * @param workItemId
     * @return
     */
    String findProcessInstanceIdByWorkItemId(String workItemId);

    /**
     * 跟据业务流程实例Id查找工作项数据列表
     *
     * @param processInstanceId
     * @return
     */
    List<WorkItem> findWorkItemsByProcessInstanceId(String processInstanceId);

    /**
     * 跟据organization查找工作项数据列表
     *
     * @param organization
     * @return
     */
    List<WorkItem> findWorkItemsByOrganization(String organization);


}
