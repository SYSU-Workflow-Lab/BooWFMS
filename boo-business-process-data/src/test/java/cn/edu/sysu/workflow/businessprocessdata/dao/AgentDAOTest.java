package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.businessprocessdata.BooBusinessProcessDataApplication;
import cn.edu.sysu.workflow.common.entity.access.Account;
import cn.edu.sysu.workflow.common.entity.access.Agent;
import cn.edu.sysu.workflow.common.enums.AgentReentrantType;
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

/**
 * {@link AgentDAO}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooBusinessProcessDataApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AgentDAOTest {

    @Autowired
    private AgentDAO agentDAO;

    private Agent agent;

    @Before
    public void setUp() {
        this.agent = new Agent();
        this.agent.setAgentId("test-agent-" + IdUtil.nextId());
        this.agent.setDisplayName("displayName");
        this.agent.setLocation("location");
        this.agent.setReentrantType(AgentReentrantType.Reentrant.ordinal());
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, agentDAO.save(agent));
        // findOne
        Assert.assertEquals(AgentReentrantType.Reentrant,
                AgentReentrantType.values()[agentDAO.findOne(agent.getAgentId()).getReentrantType()]);
    }

}
