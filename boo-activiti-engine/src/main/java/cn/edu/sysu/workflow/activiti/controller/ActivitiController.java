package cn.edu.sysu.workflow.activiti.controller;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.edu.sysu.workflow.activiti.service.ActivitiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 引擎服务调用控制器
 * @author: Gordan Lin
 * @create: 2019/9/24
 */
@RestController
@RequestMapping("/activiti-engine")
@SuppressWarnings("unchecked")
public class ActivitiController {
    private final static Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private TaskService taskService;

    /**
     * 根据key启动流程
     * @param variables
     * @param processModelKey
     * @return
     */
    @RequestMapping(value = "/startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@RequestParam Map<String, Object> variables,
                                                       @PathVariable(value = "processModelKey") String processModelKey) {
        HashMap<String, String> response = new HashMap<>();

        //启动流程
        ProcessInstance pi =  activitiService.startProcessInstanceByKey(processModelKey, variables);
        response.put("status", "success");
        response.put("message", "start process " + processModelKey + " success");
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    /**
     * 根据id启动流程
     * @param variables
     * @param processDefinitionId
     * @return
     */
    @RequestMapping(value = "/startProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam Map<String, Object> variables,
                                                      @PathVariable(value = "processDefinitionId") String processDefinitionId) {
        HashMap<String, String> response = new HashMap<>();

        //启动流程
        ProcessInstance pi =  activitiService.startProcessInstanceById(processDefinitionId, variables);
        response.put("status", "success");
        response.put("message", "start process " + processDefinitionId + " success");
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    /**
     * 根据流程实例id查询单个任务
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/getCurrentSingleTask/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentSingleTask(@PathVariable(value = "processInstanceId") String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //获取列表
        Task task = activitiService.getCurrentSingleTask(processInstanceId);
        response.put("status", "success");
        response.put("message", "get current single task processInstanceId of " + processInstanceId + " success");
        response.put("taskId", task.getId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 根据流程实例id查询任务列表
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/getCurrentTasks/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasks(@PathVariable(value = "processInstanceId") String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //获取列表
        List<Task> tasks = activitiService.getCurrentTasks(processInstanceId);
        List<String> taskIds = new ArrayList<>();
        for(Task task : tasks) {
            taskIds.add(task.getId());
        }
        response.put("status", "success");
        response.put("message", "get task list of processInstanceId of " + processInstanceId + " success");
        response.put("taskIds", JSON.toJSONString(taskIds));
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 获取指定流程的指定用户的任务列表
     * @param variables
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/getCurrentTasksOfAssignee/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasksOfAssignee(@RequestParam Map<String, Object> variables,
                                                       @PathVariable(value = "processInstanceId") String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //获取列表
        String assignee = (String) variables.get("assignee");
        List<Task> tasks = activitiService.getCurrentTasks(processInstanceId, assignee);
        List<String> taskIds = new ArrayList<>();
        for(Task task : tasks) {
            taskIds.add(task.getId());
        }
        response.put("status", "success");
        response.put("message", "get " + assignee + "'s task list of processInstanceId of " + processInstanceId + " success");
        response.put("taskIds", taskIds.toString());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    /**
     * 认领任务
     * @param variables
     * @param processInstanceId
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/claimTask/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> claimTask(@RequestParam Map<String, Object> variables,
                                       @PathVariable(value = "processInstanceId") String processInstanceId,
                                       @PathVariable(value = "taskId") String taskId) {
        HashMap<String, String> response = new HashMap<>();
        String assignee = (String) variables.get("assignee");

        //认领任务
        activitiService.claimTask(taskId, assignee);
        response.put("status", "success");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        response.put("message", "assignee " + assignee + " claim the task of " + taskId + " with taskName " + task.getName());
        logger.info("claimTask: " + assignee + " " + response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    //完成任务
    @RequestMapping(value = "/completeTask/{processDefinitionId}/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeTask(@RequestParam Map<String, Object> variables,
                                          @PathVariable(value = "processDefinitionId") String processDefinitionId ,
                                          @PathVariable(value = "processInstanceId") String processInstanceId ,
                                          @PathVariable(value = "taskId") String taskId) {

        HashMap<String, String> response = new HashMap<>();

        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            variables.put(entry.getKey(), JSON.parseObject((String)entry.getValue(), Object.class));
        }

        //完成任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        activitiService.completeTask(taskId, variables);
        response.put("status", "message");
        response.put("message", "complete task of taskId " + taskId + " with taskName" + task.getName());
        response.put("isEnded", activitiService.isEnded(processInstanceId) ? "1" : "0");
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    @RequestMapping(value = "/isEnded/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> queryIsEnded(@PathVariable(value = "processInstanceId") String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //获取列表
        boolean isEnded = activitiService.isEnded(processInstanceId);
        response.put("status", "success");
        response.put("message", "processInstanceId:" + processInstanceId + "is end?: " + isEnded);
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
