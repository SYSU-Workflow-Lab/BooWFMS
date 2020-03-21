package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.common.util.TimestampUtil;
import cn.edu.sysu.workflow.resource.BooResourceApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link WorkItemDAO}
 *
 * @author Skye
 * Created on 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooResourceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class WorkItemDAOTest {

    @Autowired
    private WorkItemDAO workItemDAO;

    private WorkItem workItem;

    @Before
    public void setUp() {
        this.workItem = new WorkItem();
        this.workItem.setWorkItemId("test-wi-" + IdUtil.nextId());
        this.workItem.setProcessInstanceId("processInstanceId");
        this.workItem.setResourceServiceId("resourceServiceId");
        this.workItem.setProcessId("processId");
        this.workItem.setBusinessObjectId("businessObjectId");
        this.workItem.setTaskId("taskId");
        this.workItem.setTaskPolymorphismId("taskPolymorphismId");
        this.workItem.setArguments("arguments");
        this.workItem.setAllocateTimestamp(TimestampUtil.getCurrentTimestamp());
        this.workItem.setLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
        this.workItem.setFinishTimestamp(TimestampUtil.getCurrentTimestamp());
        this.workItem.setStatus("status");
        this.workItem.setResourcingStatus("resourcingStatus");
        this.workItem.setLaunchAccountId("launchAccountId");
        this.workItem.setFinishAccountId("finishAccountId");
        this.workItem.setTimerTriggerId("timerTriggerId");
        this.workItem.setTimerExpiryId("timerExpiryId");
        this.workItem.setLastLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
        this.workItem.setExecuteTime(1L);
        this.workItem.setCallbackNodeId("callbackNodeId");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, workItemDAO.save(workItem));

        // update
        WorkItem temp = new WorkItem();
        temp.setWorkItemId(workItem.getWorkItemId());
        temp.setExecuteTime(2L);
        Assert.assertEquals(1, workItemDAO.update(temp));
        // findOne
        Assert.assertEquals(new Long(2), workItemDAO.findOne(workItem.getWorkItemId()).getExecuteTime());
    }

    /**
     * Test {@link WorkItemDAO#findProcessInstanceIdByWorkItemId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, workItemDAO.save(workItem));

        // findProcessInstanceIdByWorkItemId
        Assert.assertEquals("processInstanceId", workItemDAO.findProcessInstanceIdByWorkItemId(workItem.getWorkItemId()));
    }

    /**
     * Test {@link WorkItemDAO#findWorkItemsByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test3() {
        // save
        Assert.assertEquals(1, workItemDAO.save(workItem));
        workItem.setWorkItemId("test-wi-" + IdUtil.nextId());
        Assert.assertEquals(1, workItemDAO.save(workItem));

        // findProcessInstanceIdByWorkItemId
        Assert.assertEquals(2, workItemDAO.findWorkItemsByProcessInstanceId("processInstanceId").size());
    }

    /**
     * Test {@link WorkItemDAO#findWorkItemsByOrganization(String)}
     */
    @Test
    @Transactional
    public void test4() {
        // save
        Assert.assertEquals(1, workItemDAO.save(workItem));
        workItem.setWorkItemId("test-wi-" + IdUtil.nextId());
        Assert.assertEquals(1, workItemDAO.save(workItem));

        // findProcessInstanceIdByWorkItemId
        Assert.assertEquals(2, workItemDAO.findWorkItemsByOrganization("process").size());
    }

}
