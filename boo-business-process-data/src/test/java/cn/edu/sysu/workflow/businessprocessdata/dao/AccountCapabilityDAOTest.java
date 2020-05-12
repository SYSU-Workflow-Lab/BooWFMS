package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.businessprocessdata.BooBusinessProcessDataApplication;
import cn.edu.sysu.workflow.common.entity.human.AccountCapability;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link AccountCapabilityDAO}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooBusinessProcessDataApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccountCapabilityDAOTest {

    @Autowired
    private AccountCapabilityDAO accountCapabilityDAO;

    private AccountCapability accountCapability;

    @Before
    public void setUp() {

    }

    /**
     * Test {@link AccountCapabilityDAO#findAccountCapabilityByCapabilityId(String)}
     */
    @Test
    public void test1() {
        Assert.assertEquals(5, accountCapabilityDAO.findAccountCapabilityByCapabilityId("capability-6").size());
    }

}
