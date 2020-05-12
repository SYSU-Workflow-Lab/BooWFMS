package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;

/**
 * 流程实例数据库操作
 *
 * @author Skye
 * Created on 2019/12/29
 */
public interface ProcessInstanceDAO {

    /**
     * 更新流程实例，不允许更新流程实例Id
     *
     * @param processInstance <ul>
     *                        <li>processInstanceId</li>
     *                        <li>processId</li>
     *                        <li>createAccountId</li>
     *                        <li>launchAccountId</li>
     *                        <li>launchPlatform</li>
     *                        <li>launchType</li>
     *                        <li>engineId</li>
     *                        <li>resourceServiceId</li>
     *                        <li>resourceBinding</li>
     *                        <li>resourceBindingType</li>
     *                        <li>failureType</li>
     *                        <li>participantCache</li>
     *                        <li>launchTimestamp</li>
     *                        <li>finishTimestamp</li>
     *                        <li>resultType</li>
     *                        <li>tag</li>
     *                        </ul>
     * @return 修改的数据量
     */
    int update(ProcessInstance processInstance);

    /**
     * 根据流程实例Id查找流程实例数据
     *
     * @param processInstanceId
     * @return
     */
    ProcessInstance findOne(String processInstanceId);

}
