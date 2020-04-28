package cn.edu.sysu.workflow.access.service;

import cn.edu.sysu.workflow.access.BooAccessApplication;
import cn.edu.sysu.workflow.access.service.AccountService;
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

/**
 * {@link AccountService}
 *
 * @author Skye
 * Created on 2020/4/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooAccessApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    private Account account;

    @Before
    public void setUp() {
        this.account = new Account();
        this.account.setAccountId("test-account-" + IdUtil.nextId());
        this.account.setUsername("username");
        this.account.setPassword("password");
        this.account.setOrganizationName("organizationName");
        this.account.setStatus(1);
        this.account.setLevel(2);
    }

    /**
     * Test {@link AccountService#register(String, String, String, String)} )}
     */
    @Test
    public void test1() {
        Assert.assertTrue(accountService.register("username", "password", "NORMAL", "organizationName"));
    }

}
