package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.businessprocessdata.BooBusinessProcessDataApplication;
import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import cn.edu.sysu.workflow.common.util.IdUtil;
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
 * {@link BusinessProcessDAO}
 *
 * @author Skye
 * Created on 2020/4/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooBusinessProcessDataApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BusinessProcessDAOTest {

    @Autowired
    private BusinessProcessDAO businessProcessDAO;

    private BusinessProcess businessProcess;

    @Before
    public void setUp() {
        this.businessProcess = new BusinessProcess();
        this.businessProcess.setBusinessProcessId("test-bp-" + IdUtil.nextId());
        this.businessProcess.setBusinessProcessName("businessProcessName");
        this.businessProcess.setMainBusinessObjectName("mainBusinessObjectId");
        this.businessProcess.setCreatorId("creatorId");
        this.businessProcess.setLaunchCount(0);
        this.businessProcess.setSuccessCount(1);
        this.businessProcess.setAverageCost(2L);
        this.businessProcess.setStatus(1);
        this.businessProcess.setLastLaunchTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));
        // findOne
        Assert.assertEquals(businessProcess, businessProcessDAO.findOne(businessProcess.getBusinessProcessId()));

        // update
        businessProcess.setLastLaunchTimestamp(new Timestamp(System.currentTimeMillis()));
        Assert.assertEquals(1, businessProcessDAO.update(businessProcess));
        // findOne
        Assert.assertEquals(businessProcess, businessProcessDAO.findOne(businessProcess.getBusinessProcessId()));
    }

    /**
     * Test {@link BusinessProcessDAO#findBusinessProcessesByCreatorId(String}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));
        businessProcess.setBusinessProcessId("test-bp-" + IdUtil.nextId());
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));

        // findBusinessProcessesByCreatorId
        Assert.assertEquals(2, businessProcessDAO.findBusinessProcessesByCreatorId("creatorId").size());
    }

    /**
     * Test {@link BusinessProcessDAO#findBusinessProcessesByOrganization(String)}
     */
    @Test
    @Transactional
    public void test3() {
        // save
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));
        businessProcess.setBusinessProcessId("test-bp-" + IdUtil.nextId());
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));

        // findBusinessProcessesByCreatorId
        Assert.assertEquals(2, businessProcessDAO.findBusinessProcessesByOrganization("creator").size());
    }

    /**
     * Test {@link BusinessProcessDAO#checkBusinessProcessByCreatorIdAndProcessName(String, String)}
     */
    @Test
    @Transactional
    public void test4() {
        // save
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));

        // findBusinessProcessesByCreatorId
        Assert.assertTrue(businessProcessDAO.checkBusinessProcessByCreatorIdAndProcessName("creatorId", "businessProcessName"));
        Assert.assertFalse(businessProcessDAO.checkBusinessProcessByCreatorIdAndProcessName("creatorId", "businessProcessName1"));
    }

}
