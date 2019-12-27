package cn.edu.sysu.workflow.service;

import cn.edu.sysu.workflow.BooEngineApplication;
import cn.edu.sysu.workflow.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.entity.ServiceInfo;
import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.util.Objects;

/**
 * 系统定时任务服务
 *
 * @author Skye
 * Created on 2019/12/26
 */

@Service
public class ApplicationRunningHelper {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRunningHelper.class);

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    @Autowired
    private Environment environment;

    private String URL;

    @PostConstruct
    public void postConstruct() {
        try {
            URL = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port");
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setServiceInfoId(BooEngineApplication.ENGINE_ID);
            serviceInfo.setUrl(URL);
            serviceInfoDAO.saveOrUpdate(serviceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void preDestroy() {
        serviceInfoDAO.deleteByServiceInfoId(BooEngineApplication.ENGINE_ID);
    }

    /**
     * Update engine information per 10 seconds.
     */
    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void MonitorRunner() {
        try {
            StringBuilder sb = new StringBuilder();

            // CPU Load
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            double cpuValue = operatingSystemMXBean.getProcessCpuLoad();
            sb.append("当前CPU占用率：").append(cpuValue * 100).append("% | ");

            // Memory Usage
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapMemory = memoryMXBean.getHeapMemoryUsage();
            double memoryResult = heapMemory.getUsed() * 1.0 / heapMemory.getCommitted();
            sb.append("当前Memory占用率：").append(memoryResult * 100).append("% | ");

            // Tomcat Threads
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("Tomcat:name=\"http-nio-" + environment.getProperty("server.port") + "\",type=ThreadPool");
            AttributeList list = mBeanServer.getAttributes(name, new String[]{"currentThreadsBusy"});
            Attribute att = (Attribute)list.get(0);
            Integer tomcatValue  = (Integer)att.getValue();
            double maxThreads = Double.parseDouble(Objects.requireNonNull(environment.getProperty("server.tomcat.max-threads")));
            double tomcatResult = tomcatValue / maxThreads;
            sb.append("当前Tomcat并发数：").append(tomcatValue);

            ServiceInfo serviceInfo = serviceInfoDAO.findByServiceInfoId(BooEngineApplication.ENGINE_ID);
            serviceInfo.setCpuOccupancyRate(cpuValue);
            serviceInfo.setMemoryOccupancyRate(memoryResult);
            serviceInfo.setTomcatConcurrency(tomcatResult);
            serviceInfo.updateBusiness();
            serviceInfoDAO.saveOrUpdate(serviceInfo);
            sb.insert(0, "当前繁忙程度指标：" + serviceInfo.getBusiness() + "% | ");

            log.debug(sb.toString());
        } catch (MalformedObjectNameException | InstanceNotFoundException | ReflectionException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
