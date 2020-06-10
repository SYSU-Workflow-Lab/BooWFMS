package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.access.Authority;
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
 * {@link AuthorityDAO}
 *
 * @author Skye
 * Created on 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooResourceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthorityDAOTest {

    @Autowired
    private AuthorityDAO authorityDAO;

    private Authority authority;

    @Before
    public void setUp() {
        this.authority = new Authority();
        this.authority.setAuthorityId("test-authority-" + IdUtil.nextId());
        this.authority.setType("CRUD");
        this.authority.setAccountId("accountId");
        this.authority.setBusinessProcessEntityId("businessProcessEntityId");
    }

    /**
     * Test save
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, authorityDAO.save(authority));
    }

}
