package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.ExitItem;
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
 * {@link ExitItemDAO}
 *
 * @author Skye
 * Created on 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooResourceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ExitItemDAOTest {

    @Autowired
    private ExitItemDAO exitItemDAO;

    private ExitItem exitItem;

    @Before
    public void setUp() {
        this.exitItem = new ExitItem();
        this.exitItem.setExitItemId("test-ei-" + IdUtil.nextId());
        this.exitItem.setWorkItemId("workItemId");
        this.exitItem.setProcessInstanceId("processInstanceId");
        this.exitItem.setStatus(1);
        this.exitItem.setVisibility(2);
        this.exitItem.setReason("reason");
    }

    /**
     * Test save
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, exitItemDAO.save(exitItem));
    }
}
