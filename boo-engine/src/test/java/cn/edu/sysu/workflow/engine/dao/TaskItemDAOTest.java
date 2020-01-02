package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.TaskItem;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
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
 * {@link cn.edu.sysu.workflow.engine.dao.TaskItemDAO}
 *
 * @author Skye
 * Created on 2020/1/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskItemDAOTest {

    @Autowired
    private TaskItemDAO taskItemDAO;

    private TaskItem taskItem;

    @Before
    public void setUp() {
        taskItem = new TaskItem();
        taskItem.setTaskItemId("test-ti-" + IdUtil.nextId());
        taskItem.setBusinessObjectId("businessObjectId");
        taskItem.setTaskPolymorphismId("taskPolymorphismId");
        taskItem.setTaskPolymorphismName("taskPolymorphismName");
        taskItem.setBusinessRole("businessRole");
        taskItem.setPrinciple("principle");
        taskItem.setEventDescriptor("eventDescriptor");
        taskItem.setHookDescriptor("hookDescriptor");
        taskItem.setDocumentation("documentation");
        taskItem.setParameters("parameters");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, taskItemDAO.save(taskItem));
    }

}
