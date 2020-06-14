package cn.edu.sysu.workflow.activiti.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Activiti引擎服务接口
 * @author: Gordan Lin
 * @create: 2019/9/22
 **/
public interface ProcessService {

    /**
     * 根据流程定义key启动流程实例
     * @param processModelKey
     * @param variables
     * @return
     */
    public ResponseEntity<?> startProcessInstanceByKey(String processModelKey, Map<String,Object> variables);

    /**
     * 根据流程定义id启动流程实例
     * @param processInstanceId
     * @param variables
     * @return
     */
    public ResponseEntity<?> startProcessInstanceById(String processInstanceId, Map<String,Object> variables);

    /**
     * 根据流程实例获取当前任务列表
     * @param processInstanceId
     * @return
     */
    public ResponseEntity<?> getCurrentTasks(String processInstanceId);

    /**
     * 根据流程实例和配置人，获取配置人的任务列表
     * @param processInstanceId
     * @param variables
     * @return
     */
    public ResponseEntity<?> getCurrentTasks(String processInstanceId, Map<String,Object> variables);

    /**
     * 根据流程实例获取当前可执行的任务
     * @param processInstanceId
     * @return
     */
    public ResponseEntity<?> getCurrentSingleTask(String processInstanceId);

    /**
     * 认领任务
     * @param processInstanceId
     * @param taskId
     * @param assignee
     */
    public ResponseEntity<?> claimTask(String processInstanceId, String taskId, String assignee);

    /**
     * 执行任务
     * @param taskId
     * @param variables
     * @return
     */
    public ResponseEntity<?> completeTask(String taskId, String processDefinitionId, String processInstanceId, Map<String, Object> variables);

    /**
     * 执行任务
     * @param taskId
     * @param variables
     * @return
     */
    public ResponseEntity<?> completeTaskWithDelay(String taskId, String processDefinitionId, String processInstanceId, Map<String, Object> variables);

    /**
     * 根据流程实例Id判断流程实例是否结束
     * @param processInstanceId
     * @return
     */
    public ResponseEntity<?> isEnded(String processInstanceId);

}
