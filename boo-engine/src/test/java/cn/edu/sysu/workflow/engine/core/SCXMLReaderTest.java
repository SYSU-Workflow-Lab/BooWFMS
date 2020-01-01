package cn.edu.sysu.workflow.engine.core;

import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleErrorReporter;
import cn.edu.sysu.workflow.engine.core.env.jexl.JexlEvaluator;
import cn.edu.sysu.workflow.engine.core.io.BOXMLReader;
import cn.edu.sysu.workflow.engine.core.model.Data;
import cn.edu.sysu.workflow.engine.core.model.Datamodel;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import cn.edu.sysu.workflow.engine.core.model.extend.InheritableContext;
import cn.edu.sysu.workflow.engine.core.model.extend.MessageMode;
import cn.edu.sysu.workflow.engine.core.model.extend.Task;
import cn.edu.sysu.workflow.engine.core.model.extend.Tasks;
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
 * Usage : Test SCXMLReader
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SCXMLReaderTest {

    @Before
    public void Prepare() {
        BooEngineApplication.IS_LOCAL_DEBUG = true;
    }

    @Test
    @Transactional
    public void test1() throws Exception {

        String processInstanceId = "test-pi-" + IdUtil.nextId();
        long startTime = System.currentTimeMillis();
        URL url = SCXMLTestHelper.getResource("GuestOrder.xml");
        SCXML scxml = new BOXMLReader().read(url);
        long endTime = System.currentTimeMillis();
        System.out.println("COST TIME： " + (endTime - startTime) + "ms");

        Datamodel dm = scxml.getDatamodel();
        System.out.println("## guest order的data");
        for (Data data : dm.getData()) {
            System.out.println(data.getId() + ":" + data.getExpr());
        }

        InheritableContext inheritableContext = scxml.getInheritedContext();
        Tasks tasks = inheritableContext.getInheritedTasks();
        System.out.println("## guest order的可继承上下文的tasks");
        for (Task task : tasks.getTaskList()) {
            System.out.println(task.getName() + ":" + task.getId());
        }
        System.out.println("## guest order本身的tasks");
        for (Task task : scxml.getTasks().getTaskList()) {
            System.out.println(task.getName() + ":" + task.getId());
        }

        Evaluator evaluator = new JexlEvaluator();
        EventDispatcher dispatcher = new MultiStateMachineDispatcher();
        BOXMLExecutor executor = new BOXMLExecutor(evaluator, dispatcher, new SimpleErrorReporter());
        executor.setStateMachine(scxml);
        executor.setProcessInstanceId(processInstanceId);
        executor.go();


        BOXMLExecutionContext ctx = executor.getExctx();

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.MULTICAST, "GuestOrder", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "submit", null, "", 0);
        System.out.println("send submit");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "KitchenOrder", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "produced", null, "", 0);
        System.out.println("send produced");

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("passed", "1");
        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "KitchenOrder", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "testCompleted", payload, "", 0);
        System.out.println("send testCompleted");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "KitchenOrder", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "delivered", null, "", 0);
        System.out.println("send delivered");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "KitchenOrder", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "archived", null, "", 0);
        System.out.println("send archived");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.MULTICAST, "GuestOrder", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "requestCheck", null, "", 0);
        System.out.println("send requestCheck");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "GuestCheck", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "calculated", null, "", 0);
        System.out.println("send calculated");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "GuestCheck", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "paid", null, "", 0);
        System.out.println("send paid");

        dispatcher.send(processInstanceId, ctx.NodeId, "", MessageMode.TO_CHILD, "GuestCheck", "", BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR,
                "archived", null, "", 0);
        System.out.println("send archived");

        Assert.assertTrue(executor.getStatus().isFinal());
    }


}

