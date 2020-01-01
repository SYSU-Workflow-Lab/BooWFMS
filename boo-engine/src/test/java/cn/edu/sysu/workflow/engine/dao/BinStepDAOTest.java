package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BinStep;
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

import java.util.List;

/**
 * {@link cn.edu.sysu.workflow.engine.dao.BinStepDAO}
 *
 * @author Skye
 * Created on 2019/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BinStepDAOTest {

    @Autowired
    private BinStepDAO binStepDAO;

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        String binStepId = "test-bs-" + IdUtil.nextId();
        String processInstanceId = "processInstanceId";
        String parentId = "parentId";
        String notificationId = "notificationId";
        byte[] binlog = new byte[]{1, 0, 1};

        BinStep binStep = new BinStep();
        binStep.setBinStepId(binStepId);
        binStep.setProcessInstanceId(processInstanceId);
        binStep.setParentId(parentId);
        binStep.setNotificationId(notificationId);
        binStep.setBinlog(binlog);

        // save
        Assert.assertEquals(1, binStepDAO.save(binStep));
        // findOne
        Assert.assertEquals(binStep, binStepDAO.findOne(binStepId));

        // update
        binlog = new byte[]{0, 1, 0};
        binStep.setBinlog(binlog);
        Assert.assertEquals(1, binStepDAO.update(binStep));
        // findOne
        Assert.assertEquals(binStep, binStepDAO.findOne(binStepId));

    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BinStepDAO#findBinStepsByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        String binStepId1 = "test-bs-" + IdUtil.nextId();
        String binStepId2 = "test-bs-" + IdUtil.nextId();
        String processInstanceId = "processInstanceId";
        String parentId = "parentId";
        String notificationId = "notificationId";
        byte[] binlog = new byte[]{1, 0, 1};

        BinStep binStep = new BinStep();
        binStep.setBinStepId(binStepId1);
        binStep.setProcessInstanceId(processInstanceId);
        binStep.setParentId(parentId);
        binStep.setNotificationId(notificationId);
        binStep.setBinlog(binlog);

        Assert.assertEquals(1, binStepDAO.save(binStep));
        binStep.setBinStepId(binStepId2);
        Assert.assertEquals(1, binStepDAO.save(binStep));

        Assert.assertEquals(binStep, binStepDAO.findBinStepsByProcessInstanceId(processInstanceId).get(1));
    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BinStepDAO#deleteByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test3() {

        String binStepId1 = "test-bs-" + IdUtil.nextId();
        String binStepId2 = "test-bs-" + IdUtil.nextId();
        String processInstanceId = "processInstanceId";
        String parentId = "parentId";
        String notificationId = "notificationId";
        byte[] binlog = new byte[]{1, 0, 1};

        BinStep binStep = new BinStep();
        binStep.setBinStepId(binStepId1);
        binStep.setProcessInstanceId(processInstanceId);
        binStep.setParentId(parentId);
        binStep.setNotificationId(notificationId);
        binStep.setBinlog(binlog);

        Assert.assertEquals(1, binStepDAO.save(binStep));
        binStep.setBinStepId(binStepId2);
        Assert.assertEquals(1, binStepDAO.save(binStep));

        Assert.assertEquals(2, binStepDAO.deleteByProcessInstanceId(processInstanceId));
        Assert.assertNull(binStepDAO.findOne(binStepId1));
        Assert.assertNull(binStepDAO.findOne(binStepId2));
    }

}
