package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.businessprocessdata.BooBusinessProcessDataApplication;
import cn.edu.sysu.workflow.common.entity.access.Account;
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
 * {@link AccountDAO}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooBusinessProcessDataApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccountDAOTest {

    @Autowired
    private AccountDAO accountDAO;

    private Account account;

    @Before
    public void setUp() {
        this.account = new Account();
        this.account.setAccountId("test-account-" + IdUtil.nextId());
        this.account.setUsername("username");
        this.account.setPassword("password");
        this.account.setSalt("salt");
        this.account.setOrganizationId("organizationId");
        this.account.setStatus(1);
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));
        // findOne
        Assert.assertEquals("username", accountDAO.findSimpleOne(account.getAccountId()).getUsername());
        Assert.assertNull(accountDAO.findSimpleOne(account.getAccountId()).getPassword());
    }

}
