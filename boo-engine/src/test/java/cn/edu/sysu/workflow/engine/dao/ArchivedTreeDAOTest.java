package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ArchivedTree;
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
 * {@link ArchivedTreeDAO}
 *
 * @author Skye
 * Created on 2019/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ArchivedTreeDAOTest {

    @Autowired
    private ArchivedTreeDAO archivedTreeDAO;

    private ArchivedTree archivedTree;

    @Before
    public void setUp() {
        this.archivedTree = new ArchivedTree();
        this.archivedTree.setProcessInstanceId("test-at-" + IdUtil.nextId());
        this.archivedTree.setTree("tree");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, archivedTreeDAO.save(archivedTree));
        // findOne
        Assert.assertEquals(archivedTree, archivedTreeDAO.findOne(archivedTree.getProcessInstanceId()));

    }

}