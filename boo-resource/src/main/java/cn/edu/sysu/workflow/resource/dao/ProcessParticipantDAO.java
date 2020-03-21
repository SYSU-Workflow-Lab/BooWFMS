package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;

/**
 * ProcessParticipant数据库操作
 *
 * @author Skye
 * Created on 2020/1/18
 */
public interface ProcessParticipantDAO {

    /**
     * 创建流程参与者
     *
     * @param processParticipant <ul>
     *                           <li>processParticipantId</li>
     *                           <li>accountId</li>
     *                           <li>displayName</li>
     *                           <li>type</li>
     *                           <li>agentLocation</li>
     *                           <li>reentrantType</li>
     *                           <li>note</li>
     *                           </ul>
     * @return 新增的数据量
     */
    int save(ProcessParticipant processParticipant);

    /**
     * 根据账户Id查找ProcessParticipant数据
     *
     * @param accountId
     * @return
     */
    ProcessParticipant findByAccountId(String accountId);

}
