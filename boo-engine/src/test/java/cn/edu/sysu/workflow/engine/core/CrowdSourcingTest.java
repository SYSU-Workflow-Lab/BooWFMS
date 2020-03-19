package cn.edu.sysu.workflow.engine.core;

import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleErrorReporter;
import cn.edu.sysu.workflow.engine.core.env.jexl.JexlEvaluator;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.io.BOXMLReader;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import cn.edu.sysu.workflow.engine.core.model.extend.MessageMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.HashMap;

/**
 * Author: Rinkako
 * Date  : 2018/3/6
 * Usage : Test CrowdSourcing
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CrowdSourcingTest {

    @Before
    public void Prepare() {
        BooEngineApplication.IS_LOCAL_DEBUG = true;
    }

    @Test
    @Transactional
    public void test1() throws Exception {
        String processInstanceId = "test-pi-" + IdUtil.nextId();
        URL url = BOXMLTestHelper.getResource("Request.xml");
        SCXML scxml = new BOXMLReader().read(url);
        Evaluator evaluator = new JexlEvaluator();
        EventDispatcher dispatcher = new MultiStateMachineDispatcher();
        BOXMLExecutor executor = new BOXMLExecutor(evaluator, dispatcher, new SimpleErrorReporter());
        executor.setStateMachine(scxml);
        executor.setProcessInstanceId(processInstanceId);
        executor.go();

        RInstanceTree tree = InstanceManager.getInstanceTree(processInstanceId);
        BOXMLExecutionContext ctx = executor.getExctx();

        HashMap<String, Object> submitPayload = new HashMap<>();
        submitPayload.put("taskName", "What?! BO?!");
        submitPayload.put("taskDescription", "Write an article about BO");
        submitPayload.put("judgeCount", 3);
        submitPayload.put("decomposeCount", 3);
        submitPayload.put("decomposeVoteCount", 3);
        submitPayload.put("solveCount", 3);
        submitPayload.put("solveVoteCount", 3);

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_NOTIFIABLE_ID, "Request",
                "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR, "submit", submitPayload, "", 0);

        Assert.assertTrue(executor.getExctx().getScInstance().getCurrentStatus().isInState("Waiting"));
    }
}
