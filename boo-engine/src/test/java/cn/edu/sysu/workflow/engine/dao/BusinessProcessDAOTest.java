package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BusinessProcess;
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
 * {@link cn.edu.sysu.workflow.engine.dao.BusinessProcessDAO}
 *
 * @author Skye
 * Created on 2020/1/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
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
        this.businessProcess.setMainBusinessObjectId("mainBusinessObjectId");
        this.businessProcess.setCreatorId("creatorId");
        this.businessProcess.setLaunchCount(0);
        this.businessProcess.setSuccessCount(1);
        this.businessProcess.setAverageCost(2L);
        this.businessProcess.setStatus(3);
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
}
