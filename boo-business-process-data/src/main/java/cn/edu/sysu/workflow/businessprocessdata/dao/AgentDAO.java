package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.common.entity.access.Agent;

/**
 * Agent数据库操作
 *
 * @author Skye
 * Created on 2020/4/27
 */
public interface AgentDAO {

    /**
     * 创建Agent
     *
     * @param agent <ul>
     *                <li>agentId</li>
     *                <li>displayName</li>
     *                <li>location</li>
     *                <li>reentrantType</li>
     *                </ul>
     * @return 新增的数据量
     */
    int save(Agent agent);

    /**
     * 根据Agent Id查找Agent
     *
     * @param agentId
     * @return
     */
    Agent findOne(String agentId);

}
