package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BusinessObject;
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
 * {@link cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO}
 *
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
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        String businessObjectId = "test-bo-" + IdUtil.nextId();
        BusinessObject businessObject = new BusinessObject();
        businessObject.setBusinessObjectId(businessObjectId);
        businessObject.setBusinessObjectName("businessObjectName");
        businessObject.setProcessId("processId");
        businessObject.setStatus(0);
        businessObject.setContent("content");
        businessObject.setSerialization(new byte[]{1, 0, 1});
        businessObject.setBusinessRoles("businessRoles");

        // save
        Assert.assertEquals(1, businessObjectDAO.save(businessObject));
        // findOne
        Assert.assertEquals(businessObject, businessObjectDAO.findOne(businessObjectId));

        // update
        businessObject.setSerialization(new byte[]{0, 1, 0});
        Assert.assertEquals(1, businessObjectDAO.update(businessObject));
        // findOne
        Assert.assertEquals(businessObject, businessObjectDAO.findOne(businessObjectId));
    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO#findBusinessObjectsByProcessId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        Assert.assertEquals(0, businessObjectDAO.findBusinessObjectsByProcessId("1").size());
    }

}
