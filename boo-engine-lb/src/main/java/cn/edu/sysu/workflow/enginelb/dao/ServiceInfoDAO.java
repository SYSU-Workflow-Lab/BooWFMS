package cn.edu.sysu.workflow.enginelb.dao;

/**
 * 服务信息数据库操作
 *
 * @author Skye
 * Created on 2019/12/26
 */
public interface ServiceInfoDAO {

    /**
     * 随机获取引擎服务URL
     *
     * @return
     */
    String findEngineServiceUrlByRandom();

    /**
     * 获取某一位置的引擎服务URL
     *
     * @param rrCount
     * @return
     */
    String findEngineServiceUrlByRoundRobin(int rrCount);

    /**
     * 根据流程实例ID查找对应的引擎服务URL
     *
     * @param processInstanceId
     * @return
     */
    String findEngineServiceUrlByProcessInstanceId(String processInstanceId);

    /**
     * 获取引擎服务数量
     *
     * @return
     */
    Integer findEngineServiceQuantity();

}
