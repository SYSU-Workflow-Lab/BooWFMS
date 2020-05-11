package cn.edu.sysu.workflow.businessprocessdata.service;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;

import java.util.Map;

/**
 * 业务流程实例数据服务
 *
 * @author Skye
 * Created on 2020/4/20
 */
public interface ProcessInstanceService {

    /**
     * Create a process instance.
     *
     * @param pid              process id to be launched
     * @param from             launch platform
     * @param bindingType      resource binding type
     * @param launchType       process launch type
     * @param failureType      process failure catch type
     * @param binding          resource binding source, only useful when static XML binding
     * @return Runtime record package
     */
    String createProcessInstance(String pid, String from, Integer bindingType, Integer launchType, Integer failureType, String binding);

    /**
     * Check a process instance finish status.
     *
     * @param processInstanceId process instance id.
     * @return a map of status description in JSON
     */
    Map<String, String> checkFinish(String processInstanceId);

    /**
     * find a process instance.
     *
     * @param processInstanceId
     * @return ProcessInstance instance
     */
    ProcessInstance findOne(String processInstanceId);
}
