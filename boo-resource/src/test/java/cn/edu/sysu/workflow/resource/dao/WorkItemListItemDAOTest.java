package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.WorkItemListItem;
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
 * {@link WorkItemListItemDAO}
 *
 * @author Skye
 * Created on 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooResourceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class WorkItemListItemDAOTest {

    @Autowired
    private WorkItemListItemDAO workItemListItemDAO;

    private WorkItemListItem workItemListItem;

    @Before
    public void setUp() {
        this.workItemListItem = new WorkItemListItem();
        this.workItemListItem.setWorkItemListItemId("test-wili-" + IdUtil.nextId());
        this.workItemListItem.setWorkItemListId("workItemListId");
        this.workItemListItem.setWorkItemId("workItemId");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));
        // findByWorkItemListIdAndWorkItemId
        Assert.assertEquals(workItemListItem.getWorkItemListItemId(),
                workItemListItemDAO.findByWorkItemListIdAndWorkItemId("workItemListId", "workItemId").getWorkItemListItemId());

        // update
        WorkItemListItem temp = new WorkItemListItem();
        temp.setWorkItemListItemId(workItemListItem.getWorkItemListItemId());
        temp.setWorkItemId("workItemId1");
        Assert.assertEquals(1, workItemListItemDAO.update(temp));
        // findByWorkItemListIdAndWorkItemId
        Assert.assertEquals(workItemListItem.getWorkItemListItemId(),
                workItemListItemDAO.findByWorkItemListIdAndWorkItemId("workItemListId", "workItemId1").getWorkItemListItemId());

        // deleteByWorkItemListIdAndWorkItemId
        Assert.assertEquals(1, workItemListItemDAO.deleteByWorkItemListIdAndWorkItemId("workItemListId", "workItemId1"));
        // findByWorkItemListIdAndWorkItemId
        Assert.assertNull(workItemListItemDAO.findByWorkItemListIdAndWorkItemId("workItemListId", "workItemId1"));

    }

    /**
     * Test {@link WorkItemListItemDAO#deleteByWorkItemId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));
        workItemListItem.setWorkItemListItemId("test-wili-" + IdUtil.nextId());
        workItemListItem.setWorkItemListId("workItemListId1");
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));

        // deleteByWorkItemId
        Assert.assertEquals(2, workItemListItemDAO.deleteByWorkItemId("workItemId"));

        // findByWorkItemListIdAndWorkItemId
        Assert.assertNull(workItemListItemDAO.findByWorkItemListIdAndWorkItemId("workItemListId", "workItemId"));
        Assert.assertNull(workItemListItemDAO.findByWorkItemListIdAndWorkItemId("workItemListId1", "workItemId"));

    }

    /**
     * Test {@link WorkItemListItemDAO#findWorkItemListItemsByWorkItemListId(String)}
     */
    @Test
    @Transactional
    public void test3() {
        // save
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));
        workItemListItem.setWorkItemListItemId("test-wili-" + IdUtil.nextId());
        workItemListItem.setWorkItemId("workItemId1");
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));

        // findProcessInstanceIdByWorkItemId
        Assert.assertEquals(2, workItemListItemDAO.findWorkItemListItemsByWorkItemListId("workItemListId").size());
    }

    /**
     * Test {@link WorkItemListItemDAO#findWorkItemListItemsByWorkItemId(String)}
     */
    @Test
    @Transactional
    public void test4() {
        // save
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));
        workItemListItem.setWorkItemListItemId("test-wili-" + IdUtil.nextId());
        workItemListItem.setWorkItemListId("workItemListId1");
        Assert.assertEquals(1, workItemListItemDAO.save(workItemListItem));

        // findProcessInstanceIdByWorkItemId
        Assert.assertEquals(2, workItemListItemDAO.findWorkItemListItemsByWorkItemId("workItemId").size());
    }

}
