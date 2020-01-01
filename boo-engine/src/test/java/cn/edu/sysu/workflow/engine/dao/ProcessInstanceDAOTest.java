package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * {@link cn.edu.sysu.workflow.engine.dao.ProcessInstanceDAO}
 *
 * @author Skye
 * Created on 2019/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProcessInstanceDAOTest {

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        String processInstanceId = "test-pi-" + IdUtil.nextId();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessInstanceId(processInstanceId);
        processInstance.setProcessId("processId");
        processInstance.setLaunchAccountId("launchAccountId");
        processInstance.setLaunchMethod("launchMethod");
        processInstance.setLaunchType(0);
        processInstance.setEngineId("engineId");
        processInstance.setResourceServiceId("resourceServiceId");
        processInstance.setResourceBinding("resourceBinding");
        processInstance.setResourceBindingType(1);
        processInstance.setFailureType(2);
        processInstance.setParticipantCache("participantCache");
        processInstance.setResultType(4);
        processInstance.setTag("tag");

        // save
        Assert.assertEquals(1, processInstanceDAO.save(processInstance));
        // findOne
        Assert.assertEquals(processInstance, processInstanceDAO.findOne(processInstanceId));

        long currentTimestamp = System.currentTimeMillis();
        processInstance.setLaunchTimestamp(new Timestamp(currentTimestamp));
        processInstance.setFinishTimestamp(new Timestamp(currentTimestamp));
        // update
        Assert.assertEquals(1, processInstanceDAO.update(processInstance));
        // findOne
        Assert.assertEquals(processInstance, processInstanceDAO.findOne(processInstanceId));

    }

}
