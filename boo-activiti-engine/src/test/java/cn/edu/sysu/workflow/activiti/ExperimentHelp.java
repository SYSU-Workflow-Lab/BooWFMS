package cn.edu.sysu.workflow.activiti;

import org.activiti.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExperimentHelp {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private ProcessEngine processEngine;

    //批量增加流程定义数，用于验证RAM与流程定义数的关系
    @Test
    public void addProcessDefinitions() {
        String onlineShoppingPath = "processes/online-shopping.bpmn20.xml";

        //增加1000个流程定义
        int num = 1000;
        for(int i = 0; i < num; i++) {
            repositoryService.createDeployment().addClasspathResource(onlineShoppingPath).deploy();
        }
    }
}
