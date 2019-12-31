package cn.edu.sysu.workflow.engine.dao;

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
 * @see cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO
 * @author Skye
 * Created on 2019/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BusinessObjectDAOTest {

    @Autowired
    private BusinessObjectDAO businessObjectDAO;

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO#findBusinessObjectsByProcessId(String)}
     */
    @Test
    @Transactional
    public void test1() {
        Assert.assertEquals(0, businessObjectDAO.findBusinessObjectsByProcessId("1").size());
    }

}
