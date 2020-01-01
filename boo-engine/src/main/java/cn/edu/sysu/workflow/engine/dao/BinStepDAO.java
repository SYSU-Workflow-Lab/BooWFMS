package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BinStep;

import java.util.List;

/**
 * 用于流程实例单步数据快照数据库操作
 *
 * @author Skye
 * Created on 2019/12/31
 */
public interface BinStepDAO {

    /**
     * 创建流程实例单步数据快照
     *
     * @param binStep <ul>
     *                <li>binStepId</li>
     *                <li>processInstanceId</li>
     *                <li>parentId</li>
     *                <li>notificationId</li>
     *                <li>binlog</li>
     *                </ul>
     * @return 新增的数据量
     */
    int save(BinStep binStep);

    /**
     * 更新流程实例单步数据快照，不允许更新流程实例数据快照Id
     *
     * @param binStep <ul>
     *                <li>binStepId</li>
     *                <li>processInstanceId</li>
     *                <li>parentId</li>
     *                <li>notificationId</li>
     *                <li>binlog</li>
     *                </ul>
     * @return 修改的数据量
     */
    int update(BinStep binStep);

    /**
     * 根据流程实例单步数据快照Id查找流程实例单步数据快照
     *
     * @param binStepId
     * @return
     */
    BinStep findOne(String binStepId);

    /**
     * 根据流程实例Id查找流程实例单步数据快照列表
     *
     * @param processInstanceId
     * @return
     */
    List<BinStep> findBinStepsByProcessInstanceId(String processInstanceId);

    /**
     * 根据流程实例Id删除流程实例单步数据快照
     *
     * @param processInstanceId
     * @return
     */
    int deleteByProcessInstanceId(String processInstanceId);

}
