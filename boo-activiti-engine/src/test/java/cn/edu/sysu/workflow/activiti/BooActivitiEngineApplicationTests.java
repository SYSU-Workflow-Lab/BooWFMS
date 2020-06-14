package cn.edu.sysu.workflow.activiti;


import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooActivitiEngineApplicationTests {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void initDeploy() {
        String r1 = "processes/online-shopping.bpmn20.xml";
        for (int i = 0; i < 1; i++) {
            repositoryService.createDeployment().addClasspathResource(r1).deploy();
        }
    }

    @Test
    public void getProcessDefinitionId() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey("online-shopping").list();
        for (ProcessDefinition p : list) {
            System.out.println(p.getId());
        }
    }

    @Test
    public void contextDeploy() {
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);

        String r1 = "processes/leave.bpmn20.xml";
        String r2 = "processes/travel-booking-process.bpmn20.xml";

        long startTime1 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r2).deploy();
        long endTime1 = System.currentTimeMillis();
        System.out.println("first: "  + (endTime1 - startTime1));

        long startTime2 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r1).deploy();
        long endTime2 = System.currentTimeMillis();
        System.out.println("second: " + (endTime2 - startTime2));

        long startTime3 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r2).deploy();
        long endTime3 = System.currentTimeMillis();
        System.out.println("third:" + (endTime3 - startTime3));

        long startTime4 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r1).deploy();
        long endTime4 = System.currentTimeMillis();
        System.out.println("fourth: " + (endTime4 - startTime4));
    }


    @Test
    public void contextStart() {
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);

        //启动流程leave
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("apply", "zhangsan");
        variables.put("approve", "lisi");
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        endTime = System.currentTimeMillis();
        System.out.println("start: " + (endTime - startTime));

        //完成第一步申请
        String processId = processInstance.getId();
        Task task1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        startTime = System.currentTimeMillis();
        taskService.complete(task1.getId(), variables);
        endTime = System.currentTimeMillis();
        System.out.println("complete: " + (endTime - startTime));

        //完成第二步请求
        Task task2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        variables.put("pass", true);
        startTime = System.currentTimeMillis();
        taskService.complete(task2.getId(), variables);
        endTime = System.currentTimeMillis();
        System.out.print("complete: " + (endTime - startTime));

        System.out.println("完成数：" + historyService.createHistoricProcessInstanceQuery().finished().count());
    }

    /**
     * 测试单个任务的消耗时长是否一致
     */
    @Test
    public void testSingleTaskCost() {
        //启动流程leave
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("apply", "zhangsan");
        variables.put("approve", "lisi");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        ExecutionEntity executionEntity = (ExecutionEntity) processInstance;
        System.out.println(executionEntity.getActivity());

        //完成第一步申请
        String processId = processInstance.getId();
        System.out.println("processId: " + processId);
        Task task1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("task1_Id: " + task1.getId());
        System.out.println("task1_Name: " + task1.getName());
        taskService.complete(task1.getId(), variables);

        //完成第二步请求
        Task task2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        variables.put("pass", true);
        taskService.complete(task2.getId(), variables);
        System.out.println("完成数：" + historyService.createHistoricProcessInstanceQuery().finished().count());
    }

    @Test
    public void testLeave() {
        //验证是否有加载到processes下面的流程文件
        System.out.println("ok>>");
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);

        //启动流程leave
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("apply", "zhangsan");
        variables.put("approve", "lisi");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("leave", variables);
        ExecutionEntity executionEntity = (ExecutionEntity) processInstance;
        ExecutionEntity executionEntity1 = (ExecutionEntity) processInstance1;
        System.out.println(executionEntity.getActivity());
        System.out.println(executionEntity1.getActivity());

        if(executionEntity.getActivity() == executionEntity1.getActivity()) {
            System.out.println("equal");
        } else {
            System.out.println("no equal");
        }

        //完成第一步申请
        String processId = processInstance.getId();
        System.out.println("processId: " + processId);
        Task task1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("task1_Id: " + task1.getId());
        System.out.println("task1_Name: " + task1.getName());
        taskService.complete(task1.getId(), variables);

        //完成第二步请求
        Task task2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        variables.put("pass", true);
        taskService.complete(task2.getId(), variables);


        System.out.println("完成数：" + historyService.createHistoricProcessInstanceQuery().finished().count());
    }


    //测试将proceddDefinitionEntity从数据库恢复到引擎内存需要花费的时间
    @Test
    public void testRecoverProcessDefinitionEntityTime() {
        //试图排除一切初始化工作
        String modelTwo = "processes/2_model.bpmn20.xml";
        DeploymentBuilder builder1 = repositoryService.createDeployment();
        builder1.addClasspathResource(modelTwo);
        builder1.deploy();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("load-application");

        System.out.println("before recover");
        long startTime = System.currentTimeMillis();
        ProcessInstance pi1 = runtimeService.startProcessInstanceById("online-shopping:1:4");
        long endTime = System.currentTimeMillis();
        System.out.println("recover: " + (endTime - startTime));
        System.out.println("processDefinitionId:" + pi1.getProcessDefinitionId());

        System.out.println("after recover");
        //直接使用上面流程已经恢复的procssDefinition
        long startTime1 = System.currentTimeMillis();
        ProcessInstance pi2 = runtimeService.startProcessInstanceById("online-shopping:1:4");
        long endTime1 = System.currentTimeMillis();
        System.out.println("no recover: " + (endTime1 - startTime1));
        System.out.println("processDefinitionId:" + pi2.getProcessDefinitionId());

        long startTime2 = System.currentTimeMillis();
        ProcessInstance pi3 = runtimeService.startProcessInstanceById("online-shopping:1:4");
        long endTime2 = System.currentTimeMillis();
        System.out.println("no recover: " + (endTime2 - startTime2));//时间消耗大概是296ms；书本35页也有说到这种缓存带来的性能上的大提升
    }

    @Test
    public void testTravelBooking() {
        //验证是否有加载
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);
        count = 0;

        //参数设定
        String traveler = "Mike";
        String hotel = "1";
        String flight = "0";
        String car = "1";

        //启动流程:
        Map<String, Object> variables = new HashMap<String, Object>();
        Map<String, Object> subVariables = new HashMap<String, Object>();

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("travel-booking", variables);
        System.out.println(pi);

        //完成第一步：register
        Task registerTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println(registerTask.getName());
        // -- traveler认领任务
        taskService.claim(registerTask.getId(), traveler);
        System.out.println("taskId:" + registerTask.getId());
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        //进入子流程
        // -- 完成子流程第一步register
         Task registerItineraryTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- traveler 认领任务
        taskService.claim(registerItineraryTask.getId(), traveler);
        // -- -- 完成任务
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        subVariables.put("hotel", hotel);
        subVariables.put("car", car);
        subVariables.put("flight", flight);
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId(), subVariables);
        }
        // -- 子流程第二步：book
        tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        System.out.println("包容网关通过的数量：" + tasks.size());
        // -- -- 认领多个任务，包容网关
        for(Task task : tasks) {
            System.out.println("Task for " + traveler + ": " + task.getName());
            taskService.claim(task.getId(), traveler);
        }
        // -- -- 完成子流程任务
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        // -- 完成子流程第三步：prepare pay
        Task preparePayTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- 认领prepare pay
        taskService.claim(preparePayTask.getId(), traveler);
        // -- -- 完成prepare pay
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        //完成主流程pay任务
        Task payTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- 认领prepare pay
        taskService.claim(payTask.getId(), traveler);
        // -- -- 完成prepare pay
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        //判断是否完成
        System.out.println(historyService.createHistoricProcessInstanceQuery().finished().count());
    }
}
