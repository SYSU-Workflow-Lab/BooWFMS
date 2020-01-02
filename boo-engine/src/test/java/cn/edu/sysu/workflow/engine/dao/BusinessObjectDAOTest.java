package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.BusinessObject;
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
 * {@link BusinessObjectDAO}
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

    private BusinessObject businessObject;

    @Before
    public void setUp() {
        this.businessObject = new BusinessObject();
        this.businessObject.setBusinessObjectId("test-bo-" + IdUtil.nextId());
        this.businessObject.setBusinessObjectName("businessObjectName");
        this.businessObject.setProcessId("processId");
        this.businessObject.setStatus(0);
        this.businessObject.setContent("content");
        this.businessObject.setSerialization(new byte[]{1, 0, 1});
        this.businessObject.setBusinessRoles("businessRoles");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, businessObjectDAO.save(businessObject));
        // findOne
        Assert.assertEquals(businessObject, businessObjectDAO.findOne(businessObject.getBusinessObjectId()));

        // update
        businessObject.setSerialization(new byte[]{0, 1, 0});
        Assert.assertEquals(1, businessObjectDAO.update(businessObject));
        // findOne
        Assert.assertEquals(businessObject, businessObjectDAO.findOne(businessObject.getBusinessObjectId()));
    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO#findBusinessObjectsByProcessId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, businessObjectDAO.save(businessObject));

        // findBusinessObjectsByProcessId
        Assert.assertEquals(1, businessObjectDAO.findBusinessObjectsByProcessId("processId").size());
    }

}
