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
        Account account2 = new Account();
        account2.setAccountId("test-account-" + IdUtil.nextId());
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setSalt("salt");
        account2.setOrganizationName("organizationName");
        account2.setStatus(1);
        account2.setLevel(2);
        Assert.assertEquals(0, accountDAO.save(account2));
        // findOne

    }

    /**
     * Test {@link AccountDAO#findSimpleOne(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // findSimpleOne
        Assert.assertEquals("username", accountDAO.findSimpleOne(account.getAccountId()).getUsername());
        Assert.assertNull(accountDAO.findSimpleOne(account.getAccountId()).getPassword());
    }

    /**
     * Test {@link AccountDAO#findSaltByUsername(String)}
     */
    @Test
    @Transactional
    public void test3() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // findSaltByUsername
        Assert.assertEquals("salt", accountDAO.findSaltByUsername(account.getUsername()));
    }

    /**
     * Test {@link AccountDAO#checkAccountByUsernameAndPassword(String, String)}
     */
    @Test
    @Transactional
    public void test4() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // findSaltByUsername
        Assert.assertTrue(accountDAO.checkAccountByUsernameAndPassword(account.getUsername(), account.getPassword()));
        Assert.assertFalse(accountDAO.checkAccountByUsernameAndPassword(account.getUsername(), "111"));
    }

    /**
     * Test {@link AccountDAO#checkAccountByUsername(String)}
     */
    @Test
    @Transactional
    public void test9() {
        // save
        Assert.assertEquals(1, accountDAO.save(account));

        // checkAccountByUsername
        Assert.assertTrue(accountDAO.checkAccountByUsername("username"));
    }

}
