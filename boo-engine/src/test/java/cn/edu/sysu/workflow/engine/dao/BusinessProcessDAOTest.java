package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BusinessProcess;
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

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        String businessProcessId = "test-bp-" + IdUtil.nextId();
        BusinessProcess businessProcess = new BusinessProcess();
        businessProcess.setBusinessProcessId(businessProcessId);
        businessProcess.setBusinessProcessName("businessProcessName");
        businessProcess.setMainBusinessObjectId("mainBusinessObjectId");
        businessProcess.setCreatorId("creatorId");
        businessProcess.setLaunchCount(0);
        businessProcess.setSuccessCount(1);
        businessProcess.setAverageCost(2L);
        businessProcess.setStatus(3);
        businessProcess.setLastLaunchTimestamp(new Timestamp(System.currentTimeMillis()));

        // save
        Assert.assertEquals(1, businessProcessDAO.save(businessProcess));
        // findOne
        Assert.assertEquals(businessProcess, businessProcessDAO.findOne(businessProcessId));

        // update
        businessProcess.setLastLaunchTimestamp(new Timestamp(System.currentTimeMillis()));
        Assert.assertEquals(1, businessProcessDAO.update(businessProcess));
        // findOne
        Assert.assertEquals(businessProcess, businessProcessDAO.findOne(businessProcessId));
    }
}
