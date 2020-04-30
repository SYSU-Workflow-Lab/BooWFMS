package cn.edu.sysu.workflow.access.dao;

import cn.edu.sysu.workflow.access.BooAccessApplication;
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
@SpringBootTest(classes = BooAccessApplication.class)
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
        this.account.setOrganizationName("organizationName");
        this.account.setStatus(1);
        this.account.setLevel(2);
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));
        // findSimpleOne
        Assert.assertEquals("username", accountDAO.findSimpleOne(account.getAccountId()).getUsername());
        Assert.assertNull(accountDAO.findSimpleOne(account.getAccountId()).getPassword());

        // update
        account.setLevel(3);
        Assert.assertEquals(1, accountDAO.update(account));
        // findSimpleOne
        Assert.assertEquals(3, accountDAO.findSimpleOne(account.getAccountId()).getLevel().intValue());

        // delete
        accountDAO.deleteByUsername("username");
        // findSimpleOne
        Assert.assertNull(accountDAO.findSimpleOne(account.getAccountId()));

    }

    /**
     * Test {@link AccountDAO#findSaltByUsername(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // findSaltByUsername
        Assert.assertEquals("salt", accountDAO.findSaltByUsername(account.getUsername()));
    }

    /**
     * Test {@link AccountDAO#findAccountIdByUsernameAndPassword(String, String)}
     */
    @Test
    @Transactional
    public void test3() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // findSaltByUsername
        Assert.assertEquals(account.getAccountId(), accountDAO.findAccountIdByUsernameAndPassword(account.getUsername(), account.getPassword()));
        Assert.assertNull(accountDAO.findAccountIdByUsernameAndPassword(account.getUsername(), "111"));
    }

    /**
     * Test {@link AccountDAO#checkAccountByUsername(String)}
     */
    @Test
    @Transactional
    public void test4() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // checkAccountByUsername
        Assert.assertTrue(accountDAO.checkAccountByUsername("username"));
    }

}
