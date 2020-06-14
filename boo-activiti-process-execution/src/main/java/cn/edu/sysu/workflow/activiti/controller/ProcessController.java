package cn.edu.sysu.workflow.activiti.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.edu.sysu.workflow.activiti.service.ProcessService;
import cn.edu.sysu.workflow.activiti.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 引擎服务调用控制器
 * @author: Gordan Lin
 * @create: 2019/9/24
 */
@RestController
@RequestMapping("/activiti-process-execution")
@SuppressWarnings("unchecked")
public class ProcessController {
    private final static Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private ProcessService processService;

    /**
     * 根据key启动流程
     * @param variables
     * @param processModelKey
     * @return
     */
    @RequestMapping(value = "/startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@RequestParam(required = false) Map<String, Object> variables,
                                                       @PathVariable(value = "processModelKey", required = false) String processModelKey) {
        HashMap<String, String> response = new HashMap<>();

        //参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if (variables == null) missingParams.add("variables");
        if (processModelKey == null) missingParams.add("processModelKey");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        logger.info("startProcessInstanceByKey");

        //启动流程
        return  processService.startProcessInstanceByKey(processModelKey, variables);
    }

    /**
     * 根据id启动流程
     * @param variables
     * @param processDefinitionId
     * @return
     */
    @RequestMapping(value = "/startProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam(required = false) Map<String, Object> variables,
                                                      @PathVariable(value = "processDefinitionId", required = false) String processDefinitionId) {
        HashMap<String, String> response = new HashMap<>();

        //参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if (variables == null) missingParams.add("variables");
        if (processDefinitionId == null) missingParams.add("processDefinitionId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        logger.info("startProcessInstanceById");

        //启动流程
        return processService.startProcessInstanceById(processDefinitionId, variables);
    }

    /**
     * 根据流程实例id查询单个任务
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/getCurrentSingleTask/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentSingleTask(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if(processInstanceId == null) missingParams.add("processInstanceId");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", " required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        logger.info("getCurrentSingleTask");

        //获取列表
        return processService.getCurrentSingleTask(processInstanceId);
    }

    /**
     * 根据流程实例id查询任务列表
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/getCurrentTasks/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasks(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        logger.info("getCurrentTasks");

        //获取列表
        return processService.getCurrentTasks(processInstanceId);
    }

    /**
     * 获取指定流程的指定用户的任务列表
     * @param assignee
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/getCurrentTasksOfAssignee/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasksOfAssignee(@RequestParam(value = "assignee", required = false) String assignee,
                                                       @PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (processInstanceId == null) missingParams.add("pid");
        if (assignee == null) missingParams.add("assignee");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        logger.info("getCurrentTasksOfAssignee");

        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", assignee);
        //获取列表
        return processService.getCurrentTasks(processInstanceId, variables);
    }

    /**
     * 认领任务
     * @param assignee
     * @param processInstanceId
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/claimTask/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> claimTask(@RequestParam(required = false) String assignee,
                                       @PathVariable(value = "processInstanceId", required = false) String processInstanceId,
                                       @PathVariable(value = "taskId", required = false) String taskId) {

        HashMap<String, String> response = new HashMap<>();
        //参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if (assignee == null) missingParams.add("assignee");
        if (taskId == null) missingParams.add("taskId");
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        logger.info("claimTask");

        //认领任务
        return processService.claimTask(processInstanceId, taskId, assignee);
    }

    //完成任务
    @RequestMapping(value = "/completeTask/{processDefinitionId}/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeTask(@RequestParam(required = false) Map<String, Object> variables,
                                          @PathVariable(value = "processDefinitionId", required = false) String processDefinitionId ,
                                          @PathVariable(value = "processInstanceId", required = false) String processInstanceId ,
                                          @PathVariable(value = "taskId", required = false) String taskId) {

        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (variables == null) missingParams.add("variables");
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (processDefinitionId == null) missingParams.add("processDefinitionId");
        if (taskId == null) missingParams.add("taskId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            variables.put(entry.getKey(), JSON.parseObject((String)entry.getValue(), Object.class));
        }
        logger.info("completeTask");

        //完成任务
        return processService.completeTask(taskId, processDefinitionId, processInstanceId, variables);
    }

    //延时完成任务
    @RequestMapping(value = "/completeTaskWithDelay/{processDefinitionId}/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeTaskWithDelay(@RequestParam(required = false) Map<String, Object> variables,
                                          @PathVariable(value = "processDefinitionId", required = false) String processDefinitionId ,
                                          @PathVariable(value = "processInstanceId", required = false) String processInstanceId ,
                                          @PathVariable(value = "taskId", required = false) String taskId) {

        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (variables == null) missingParams.add("variables");
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (processDefinitionId == null) missingParams.add("processDefinitionId");
        if (taskId == null) missingParams.add("taskId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            variables.put(entry.getKey(), JSON.parseObject((String) entry.getValue(), Object.class));
        }
        logger.info("completeTaskWithDelay");

        //完成任务
        return processService.completeTaskWithDelay(taskId, processDefinitionId, processInstanceId, variables);
    }

}
