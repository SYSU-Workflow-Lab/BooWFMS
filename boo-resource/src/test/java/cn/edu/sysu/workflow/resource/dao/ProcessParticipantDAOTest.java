package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.util.IdUtil;
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
 * {@link ProcessParticipantDAO}
 *
 * @author Skye
 * Created on 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooResourceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProcessParticipantDAOTest {

    @Autowired
    private ProcessParticipantDAO processParticipantDAO;

    private ProcessParticipant processParticipant;

    @Before
    public void setUp() {
        this.processParticipant = new ProcessParticipant();
        this.processParticipant.setProcessParticipantId("test-pp-" + IdUtil.nextId());
        this.processParticipant.setAccountId("accountId");
        this.processParticipant.setDisplayName("displayName");
        this.processParticipant.setType(1);
        this.processParticipant.setAgentLocation("agentLocation");
        this.processParticipant.setReentrantType(2);
        this.processParticipant.setNote("note");
    }

    /**
     * Test save
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, processParticipantDAO.save(processParticipant));
    }

    /**
     * Test {@link ProcessParticipantDAO#findByAccountId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, processParticipantDAO.save(this.processParticipant));

        // findByAccountId
        Assert.assertEquals(this.processParticipant.getProcessParticipantId(), processParticipantDAO.findByAccountId("accountId").getProcessParticipantId());
    }

}
