package cn.edu.sysu.workflow.dao;

import cn.edu.sysu.workflow.BooEngineApplication;
import cn.edu.sysu.workflow.entity.ServiceInfo;
import cn.edu.sysu.workflow.util.IdUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Skye
 * Created on 2019/12/28
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ServiceInfoDAOTest {

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    private String serviceInfoId = "test-engine-" + IdUtil.nextId();

    @Test
    @Transactional
    public void testAllMethods() {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceInfoId(this.serviceInfoId);
        serviceInfo.setUrl("https://test.com");
        serviceInfo.setActive(true);
        serviceInfo.setBusiness(1.0);
        serviceInfo.setCpuOccupancyRate(2.0);
        serviceInfo.setMemoryOccupancyRate(3.0);
        serviceInfo.setTomcatConcurrency(4.0);
        serviceInfo.setWorkItemCount(5.0);

        // save
        Assert.assertEquals(1, serviceInfoDAO.save(serviceInfo));

        // findByServiceInfoId
        Assert.assertTrue(serviceInfoDAO.findByServiceInfoId(this.serviceInfoId).isActive());

        // update
        serviceInfo.setActive(false);
        Assert.assertEquals(1, serviceInfoDAO.update(serviceInfo));

        // findByServiceInfoId
        Assert.assertFalse(serviceInfoDAO.findByServiceInfoId(this.serviceInfoId).isActive());

        // delete
        Assert.assertEquals(1, serviceInfoDAO.deleteByServiceInfoId(this.serviceInfoId));

        // findByServiceInfoId
        Assert.assertNull(serviceInfoDAO.findByServiceInfoId(this.serviceInfoId));
    }
}
