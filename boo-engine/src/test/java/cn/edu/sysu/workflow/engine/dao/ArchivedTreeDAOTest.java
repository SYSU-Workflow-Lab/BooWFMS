package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ArchivedTree;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * {@link cn.edu.sysu.workflow.engine.dao.ArchivedTreeDAO}
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

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        String processInstanceId = "test-at-" + IdUtil.nextId();

        ArchivedTree archivedTree = new ArchivedTree();
        archivedTree.setProcessInstanceId(processInstanceId);
        archivedTree.setTree("tree");

        // save
        Assert.assertEquals(1, archivedTreeDAO.save(archivedTree));
        // findOne
        Assert.assertEquals(archivedTree, archivedTreeDAO.findOne(processInstanceId));

    }

}