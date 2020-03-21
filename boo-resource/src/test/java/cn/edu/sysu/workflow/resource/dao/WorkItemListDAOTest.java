package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.WorkItemList;
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
 * {@link WorkItemListDAO}
 *
 * @author Skye
 * Created on 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooResourceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class WorkItemListDAOTest {

    @Autowired
    private WorkItemListDAO workItemListDAO;

    private WorkItemList workItemList;

    @Before
    public void setUp() {
        this.workItemList = new WorkItemList();
        this.workItemList.setWorkItemListId("test-wil-" + IdUtil.nextId());
        this.workItemList.setOwnerAccountId("ownerAccountId");
        this.workItemList.setType(1);
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, workItemListDAO.save(workItemList));
    }

    /**
     * Test {@link WorkItemListDAO#findByOwnerAccountIdAndType(String, Integer)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, workItemListDAO.save(workItemList));

        // findProcessInstanceIdByWorkItemId
        Assert.assertEquals(workItemList.getWorkItemListId(),
                workItemListDAO.findByOwnerAccountIdAndType("ownerAccountId", 1).getWorkItemListId());
    }

}
