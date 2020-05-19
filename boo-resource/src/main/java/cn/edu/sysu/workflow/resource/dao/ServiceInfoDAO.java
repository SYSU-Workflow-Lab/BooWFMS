package cn.edu.sysu.workflow.resource.dao;

/**
 * 服务信息数据库操作
 *
 * @author Skye
 * Created on 2019/12/26
 */
public interface ServiceInfoDAO {

    /**
     * 根据流程实例ID查找对应的引擎服务URL
     *
     * @param processInstanceId
     * @return
     */
    String findEngineServiceUrlByProcessInstanceId(String processInstanceId);

}
