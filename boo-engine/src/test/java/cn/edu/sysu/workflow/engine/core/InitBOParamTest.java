package cn.edu.sysu.workflow.engine.core;

import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleErrorReporter;
import cn.edu.sysu.workflow.engine.core.env.jexl.JexlEvaluator;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.io.BOXMLReader;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import cn.edu.sysu.workflow.engine.core.model.extend.MessageMode;
import cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

/**
 * Author: Rinkako
 * Date  : 2018/3/6
 * Usage : Test InitBOParam
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class InitBOParamTest {

    @Autowired
    private ProcessInstanceManagementService processInstanceManagementService;

    @Before
    public void Prepare() {
        BooEngineApplication.IS_LOCAL_DEBUG = true;
    }

    @Test
    @Transactional
    public void test1() throws Exception {
        String processInstanceId = "test-pi-" + IdUtil.nextId();
        URL url = BOXMLTestHelper.getResource("InitBOTestMain.xml");
        SCXML scxml = new BOXMLReader().read(url);
        Evaluator evaluator = new JexlEvaluator();
        EventDispatcher dispatcher = new MultiStateMachineDispatcher();
        BOXMLExecutor executor = new BOXMLExecutor(evaluator, dispatcher, new SimpleErrorReporter());
        executor.setStateMachine(scxml);
        executor.setProcessInstanceId(processInstanceId);

        long startTime = System.currentTimeMillis();
        executor.go();
        long endTime = System.currentTimeMillis();
        System.out.println("COST TIME： " + (endTime - startTime) + "ms");

        RInstanceTree tree = InstanceManager.getInstanceTree(processInstanceId);
        BOXMLExecutionContext ctx = executor.getExctx();
        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_NOTIFIABLE_ID, "InitBOTestSub_1", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "stop", null, "", 0);
        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_NOTIFIABLE_ID, "InitBOTestSub_0", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "stop", null, "", 0);
        Assert.assertEquals(tree.Root.getExect().getScInstance().getGlobalContext().getVars().get("finishCount"), 2);
        Assert.assertFalse(executor.getStatus().isFinal());
        String st = JsonUtil.jsonSerialization(processInstanceManagementService.getSpanTreeDescriptor(processInstanceId), processInstanceId);
        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_NOTIFIABLE_ID, "InitBOTestSub_2", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "stop", null, "", 0);
        Assert.assertEquals(tree.Root.getExect().getScInstance().getGlobalContext().getVars().get("finishCount"), 3);
        Assert.assertTrue(executor.getStatus().isFinal());
    }
}
