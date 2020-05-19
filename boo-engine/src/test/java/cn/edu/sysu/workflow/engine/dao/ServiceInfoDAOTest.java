package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ServiceInfo;
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
 * {@link ServiceInfoDAO}
 *
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

    private ServiceInfo serviceInfo;

    @Before
    public void setUp() {
        this.serviceInfo = new ServiceInfo();
        this.serviceInfo.setServiceInfoId("test-resource-" + IdUtil.nextId());
        this.serviceInfo.setUrl("https://test.com");
        this.serviceInfo.setActive(true);
        this.serviceInfo.setBusiness(1.0);
        this.serviceInfo.setCpuOccupancyRate(2.0);
        this.serviceInfo.setMemoryOccupancyRate(3.0);
        this.serviceInfo.setTomcatConcurrency(4.0);
        this.serviceInfo.setWorkItemCount(5.0);
    }

    /**
     * Test CRU
     */
    @Test
    @Transactional
    public void test1() {
        // save
        Assert.assertEquals(1, serviceInfoDAO.save(serviceInfo));
        // findOne
        Assert.assertTrue(serviceInfoDAO.findOne(serviceInfo.getServiceInfoId()).isActive());

        // update
        serviceInfo.setActive(false);
        Assert.assertEquals(1, serviceInfoDAO.update(serviceInfo));
        // findOne
        Assert.assertFalse(serviceInfoDAO.findOne(serviceInfo.getServiceInfoId()).isActive());

        // delete
        Assert.assertEquals(1, serviceInfoDAO.deleteByServiceInfoId(serviceInfo.getServiceInfoId()));
        // findOne
        Assert.assertNull(serviceInfoDAO.findOne(serviceInfo.getServiceInfoId()));
    }
}
