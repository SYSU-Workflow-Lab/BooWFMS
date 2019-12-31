package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.common.entity.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see cn.edu.sysu.workflow.engine.dao.ServiceInfoDAO
 * @author Skye
 * Created on 2019/12/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ServiceInfoDAOTest {

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    /**
     * Test CRU
     */
    @Test
    @Transactional
    public void test1() {
        String serviceInfoId = "test-engine-" + IdUtil.nextId();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceInfoId(serviceInfoId);
        serviceInfo.setUrl("https://test.com");
        serviceInfo.setActive(true);
        serviceInfo.setBusiness(1.0);
        serviceInfo.setCpuOccupancyRate(2.0);
        serviceInfo.setMemoryOccupancyRate(3.0);
        serviceInfo.setTomcatConcurrency(4.0);
        serviceInfo.setWorkItemCount(5.0);

        // save
        Assert.assertEquals(1, serviceInfoDAO.save(serviceInfo));
        // findOne
        Assert.assertTrue(serviceInfoDAO.findOne(serviceInfoId).isActive());

        // update
        serviceInfo.setActive(false);
        Assert.assertEquals(1, serviceInfoDAO.update(serviceInfo));
        // findOne
        Assert.assertFalse(serviceInfoDAO.findOne(serviceInfoId).isActive());

        // delete
        Assert.assertEquals(1, serviceInfoDAO.deleteByServiceInfoId(serviceInfoId));
        // findOne
        Assert.assertNull(serviceInfoDAO.findOne(serviceInfoId));
    }

    /**
     * Test {@link cn.edu.sysu.workflow.engine.dao.ServiceInfoDAO#findResourceServiceUrlByProcessInstanceId(String)}
     */
    @Test
    @Transactional
    public void test2() {
        String processInstanceId = "test-pi-" + IdUtil.nextId();
        String resourceServiceId = "test-ri-123";
        String resourceServiceUrl = "http://test.ri.com";

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessInstanceId(processInstanceId);
        processInstance.setResourceServiceId(resourceServiceId);
        processInstanceDAO.save(processInstance);

        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceInfoId(resourceServiceId);
        serviceInfo.setUrl(resourceServiceUrl);
        serviceInfoDAO.save(serviceInfo);

        Assert.assertEquals(resourceServiceUrl, serviceInfoDAO.findResourceServiceUrlByProcessInstanceId(processInstanceId));
    }
}
