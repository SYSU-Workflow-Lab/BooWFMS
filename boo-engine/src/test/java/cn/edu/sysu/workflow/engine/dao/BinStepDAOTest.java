package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BinStep;
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

    private BinStep binStep;

    @Before
    public void setUp() {
        this.binStep = new BinStep();
        this.binStep.setBinStepId("test-bs-" + IdUtil.nextId());
        this.binStep.setProcessInstanceId("processInstanceId");
        this.binStep.setParentId("parentId");
        this.binStep.setNotificationId("notificationId");
        this.binStep.setBinlog(new byte[]{1, 0, 1});
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, binStepDAO.save(binStep));
        // findOne
        Assert.assertEquals(binStep, binStepDAO.findOne(binStep.getBinStepId()));

        // update
        binStep.setBinlog(new byte[]{0, 1, 0});
        Assert.assertEquals(1, binStepDAO.update(binStep));
        // findOne
        Assert.assertEquals(binStep, binStepDAO.findOne(binStep.getBinStepId()));

    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BinStepDAO#findBinStepsByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, binStepDAO.save(binStep));

        // save
        binStep.setBinStepId("test-bs-" + IdUtil.nextId());
        Assert.assertEquals(1, binStepDAO.save(binStep));

        // findBinStepsByProcessInstanceId
        Assert.assertEquals(binStep, binStepDAO.findBinStepsByProcessInstanceId(binStep.getProcessInstanceId()).get(1));
    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BinStepDAO#deleteByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test3() {
        // save
        Assert.assertEquals(1, binStepDAO.save(binStep));

        // save
        binStep.setBinStepId("test-bs-" + IdUtil.nextId());
        Assert.assertEquals(1, binStepDAO.save(binStep));

        // deleteByProcessInstanceId
        Assert.assertEquals(2, binStepDAO.deleteByProcessInstanceId(binStep.getProcessInstanceId()));

        // deleteByProcessInstanceId
        Assert.assertEquals(0, binStepDAO.findBinStepsByProcessInstanceId(binStep.getProcessInstanceId()).size());
    }

}
