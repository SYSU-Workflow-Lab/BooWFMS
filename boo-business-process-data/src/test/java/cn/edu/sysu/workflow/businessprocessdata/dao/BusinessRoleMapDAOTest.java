package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.businessprocessdata.BooBusinessProcessDataApplication;
import cn.edu.sysu.workflow.common.entity.BusinessRoleMap;
import cn.edu.sysu.workflow.common.util.IdUtil;
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
 * {@link BusinessRoleMap}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooBusinessProcessDataApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BusinessRoleMapDAOTest {

    @Autowired
    private BusinessRoleMapDAO businessRoleMapDAO;

    private BusinessRoleMap businessRoleMap;

    @Before
    public void setUp() {
        this.businessRoleMap = new BusinessRoleMap();
        this.businessRoleMap.setBusinessRoleMapId("test-brm-" + IdUtil.nextId());
        this.businessRoleMap.setProcessInstanceId("processInstanceId");
        this.businessRoleMap.setBusinessRoleName("businessRoleName");
        this.businessRoleMap.setOrganizationId("organizationId");
        this.businessRoleMap.setMappedId("mappedId");
        this.businessRoleMap.setDataVersion("dataVersion");
    }

    /**
     * Test CRUD
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, businessRoleMapDAO.save(businessRoleMap));
        // findOne

    }

    /**
     * Test {@link BusinessRoleMapDAO#findBusinessRoleMapsByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        // save
        Assert.assertEquals(1, businessRoleMapDAO.save(businessRoleMap));
        businessRoleMap.setBusinessRoleMapId("test-brm-" + IdUtil.nextId());
        Assert.assertEquals(1, businessRoleMapDAO.save(businessRoleMap));

        // findBusinessRoleMapsByProcessInstanceId
        Assert.assertEquals(2, businessRoleMapDAO.findBusinessRoleMapsByProcessInstanceId(businessRoleMap.getProcessInstanceId()).size());
    }
}
