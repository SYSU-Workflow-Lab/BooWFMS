package cn.edu.sysu.workflow.activiti.service.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.sysu.workflow.activiti.service.ActivitiService;

import java.util.List;
import java.util.Map;

/**
 * 引擎服务实现类
 * @author: Gordan Lin
 * @create: 2019/9/22
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    private final static Logger logger = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    public ProcessInstance startProcessInstanceByKey(String processModelKey, Map<String,Object> variables) {
        return runtimeService.startProcessInstanceByKey(processModelKey, variables);
    }

    public ProcessInstance startProcessInstanceById(String processInstanceId, Map<String,Object> variables) {
        return runtimeService.startProcessInstanceById(processInstanceId, variables);
    }

    public List<Task> getCurrentTasks(String processInstancedId) {
        return taskService.createTaskQuery().processInstanceId(processInstancedId).list();
    }

    public List<Task> getCurrentTasks(String processInstancedId, String assignee) {
        return taskService.createTaskQuery().processInstanceId(processInstancedId).taskAssignee(assignee).list();
    }

    public Task getCurrentSingleTask(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    public void claimTask(String taskId, String assignee) {
        taskService.claim(taskId, assignee);
    }

    public boolean completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
        return true;
    }

    public boolean isEnded(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(processInstance != null) {
            return processInstance.isEnded();
        }
        return true;
    }

}
