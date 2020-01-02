package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import org.junit.Assert;
import org.junit.Before;
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

    private ProcessInstance processInstance;

    @Before
    public void setUp() {
        this.processInstance = new ProcessInstance();
        this.processInstance.setProcessInstanceId("test-pi-" + IdUtil.nextId());
        this.processInstance.setProcessId("processId");
        this.processInstance.setLaunchAccountId("launchAccountId");
        this.processInstance.setLaunchMethod("launchMethod");
        this.processInstance.setLaunchType(0);
        this.processInstance.setEngineId("engineId");
        this.processInstance.setResourceServiceId("resourceServiceId");
        this.processInstance.setResourceBinding("resourceBinding");
        this.processInstance.setResourceBindingType(1);
        this.processInstance.setFailureType(2);
        this.processInstance.setParticipantCache("participantCache");
        this.processInstance.setResultType(4);
        this.processInstance.setTag("tag");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, processInstanceDAO.save(processInstance));
        // findOne
        Assert.assertEquals(processInstance, processInstanceDAO.findOne(processInstance.getProcessInstanceId()));

        long currentTimestamp = System.currentTimeMillis();
        processInstance.setLaunchTimestamp(new Timestamp(currentTimestamp));
        processInstance.setFinishTimestamp(new Timestamp(currentTimestamp));
        // update
        Assert.assertEquals(1, processInstanceDAO.update(processInstance));
        // findOne
        Assert.assertEquals(processInstance, processInstanceDAO.findOne(processInstance.getProcessInstanceId()));

    }

}
