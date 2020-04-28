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

    /**
     * Test {@link AccountService#register(String, String, String, String)} )}
     */
    @Test
    public void test1() {
        accountService.register("username", "password", "NORMAL", "organizationName");
    }

    /**
     * Test {@link AccountService#login(String, String)}
     */
    @Test
    public void test2() {
        Assert.assertTrue(accountService.login("username", "password"));
    }

}
