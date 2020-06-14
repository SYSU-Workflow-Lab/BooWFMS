package cn.edu.sysu.workflow.activiti.scheduler.rule;

import com.google.common.util.concurrent.AtomicDouble;
import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Least Busyness with Engine Group Scheduling（LBEGS）
 * 带引擎组的最小繁忙程度调度算法
 * 超载设置：引擎繁忙度超过多少就从全部引擎实例上选择繁忙度最小的，可以作为一个在均衡性和缓存命中率之间的权衡因子
 * 选择策略：
 *      1）第一次执行流程定义的执行请求直接获取繁忙度最小的服务器
 *      2）非第一次执行流程定义的执行请求从历史执行引擎获取繁忙度最小的服务器，
 *         如果判断超过超载设置，就从全局获取繁忙度最小的服务器
 * @author: Gordan Lin
 * @create: 2019/12/19
 **/
public class LBEGSRule extends RoundRobinRule {

    private static Logger logger = LoggerFactory.getLogger(LBEGSRule.class);

    // 过载阈值
    private static final double THRESHOLD = 0.6;

    // 引擎能处理的最大请求数
    private static final int ENGINE_MAX_REQUEST = 30;

    // 参考历史数据的度
    private static final double HISTORY_RATE = 0.6;

    // 维护引擎组
    private final Map<String, Set<Server>> proDefinitionIdToServerGroup =
            Collections.synchronizedMap(new LinkedHashMap<>(200, 0.75f, true));

    // 引擎处理的请求数
    private final Map<Server, AtomicInteger> serverRequestCounts = new ConcurrentHashMap<>();

    // 引擎的繁忙度
    private final Map<Server, AtomicDouble> serverBusyness = new ConcurrentHashMap<>();
    private final Map<Server, AtomicInteger> serverRequestCountsIn5seconds = new ConcurrentHashMap<>();

    public static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

    public FileWriter writerForBusyness = null;

    private boolean isInitial = false; //是否初始化过init

    public void init() {
        List<Server> servers = getLoadBalancer().getAllServers();
        for(Server server : servers) {
            this.serverRequestCounts.put(server, new AtomicInteger());
            this.serverBusyness.put(server, new AtomicDouble());
            this.serverRequestCountsIn5seconds.put(server, new AtomicInteger());
        }
        try {
            writerForBusyness = new FileWriter("D:\\lb\\LBLMB_busyness.txt");
        } catch (IOException e) {

        }
        LBEGSRule.UpdateBusynessTask updateBusynessTask = new LBEGSRule.UpdateBusynessTask();
        LBEGSRule.WriteBusynessTask writeBusynessTask = new LBEGSRule.WriteBusynessTask();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(updateBusynessTask, 1, 1, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(writeBusynessTask, 5, 5, TimeUnit.SECONDS);
    }

    public LBEGSRule() {
        super();
    }

    public Server chooseServer(ILoadBalancer lb, Object key) {
        if(lb == null) {
            logger.warn("no load balancer");
            return null;
        }

        LoadBalancerStats loadBalancerStats = ((AbstractLoadBalancer)lb).getLoadBalancerStats();
        if (loadBalancerStats == null) {
            return super.choose(key);
        }

        Server server = null;
        List<Server> reachableServers = lb.getReachableServers();
        int upCount = reachableServers.size();
        if((upCount == 0)) {
            logger.warn("no up servers available from load balancer");
            return null;
        }

        server = chooseOneServer(reachableServers, loadBalancerStats, key);
        if (server == null) {
            return super.choose(key);
        } else {
            return server;
        }
    }

    private Server chooseOneServer(List<Server> reachableServers, LoadBalancerStats loadBalancerStats, Object key) {
        if (loadBalancerStats == null) {
            logger.warn("no statistics, nothing to do so");
            return null;
        }
        String processDefinitionId = (String) key;
        Set<Server> servers = proDefinitionIdToServerGroup.get(processDefinitionId);
        Server result = null;

        // 第一次执行流程定义
        if (servers == null || servers.size() == 0) {
            //获取负载最小
            result = getMinBusynessServer(reachableServers);
            servers = new HashSet<>();
            servers.add(result);
            proDefinitionIdToServerGroup.put(processDefinitionId, servers);
        } else {
            List<Server> previousServerList = new ArrayList<>(servers);
            result = chooseMinBusynessServerFromServerGroup(reachableServers, previousServerList, loadBalancerStats);
            if (result == null) {
                result = super.choose(key);
            }
            servers.add(result);
            proDefinitionIdToServerGroup.put(processDefinitionId, servers);
        }
        return result;
    }

    private Server chooseMinBusynessServerFromServerGroup(List<Server> reachableServers, List<Server> previousServerList, LoadBalancerStats loadBalancerStats) {
        Server result = null;
        double min = Integer.MAX_VALUE;
        for(Server server : previousServerList) {
            double busyness = this.serverBusyness.get(server).doubleValue();
            if (min > busyness) {
                min = busyness;
                result = server;
            }
        }
        //如果最小的繁忙度大于 THRESHOLD 的话，就从全局选取
        if(min > THRESHOLD) {
            logger.info("最小繁忙度大于阀值，全局获取");
            result = getMinBusynessServer(reachableServers);
        }
        return result;
    }

    //获取繁忙度最小的引擎
    public Server getMinBusynessServer(List<Server> servers) {
        Server target = null;
        double min = Integer.MAX_VALUE;
        for(Server server : servers) {
            double busyness = this.serverBusyness.get(server).doubleValue();
            if (min > busyness) {
                min = busyness;
                target = server;
            }
        }
        return target;
    }

    public Server choose(Object key) {
        if (!isInitial) {
            synchronized ((Object) isInitial) {
                if(!isInitial) {
                    init();
                    isInitial = true;
                }
            }
        }

        //获取请求中processDefinitionId的值
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        int startIndex = uri.indexOf('/', 1)+1;
        int endIndex = uri.indexOf('/', startIndex);
        if (endIndex == -1) endIndex = uri.length();
        String processDefinitionId = uri.substring(startIndex, endIndex);

        Server server = chooseServer(getLoadBalancer(), processDefinitionId);
        serverRequestCounts.get(server).incrementAndGet();
        return server;
    }

    /**
     * 更新引擎繁忙度
     */
    public synchronized void updateServerHistoryBusyness() {
        List<Server> servers = getLoadBalancer().getAllServers();
        String log2 = "lbegs:";
        for(Server server : servers) {
            AtomicInteger requestCount = this.serverRequestCounts.get(server);
            double curBusyness = requestCount.doubleValue() / ENGINE_MAX_REQUEST;
            double busyness = this.serverBusyness.get(server).doubleValue() * HISTORY_RATE
                    + curBusyness * (1 - HISTORY_RATE);
            log2 += server.getHostPort() + ": " + requestCount.intValue() + " - " + busyness + "  ";
            this.serverRequestCountsIn5seconds.get(server).addAndGet(requestCount.intValue());
            this.serverRequestCounts.get(server).set(0);
            this.serverBusyness.get(server).set(busyness);
        }
        logger.info(log2);
    }

    /**
     * 记录引擎繁忙度文件
     */
    public synchronized void writeBusyness() {
        List<Server> servers = getLoadBalancer().getAllServers();
        String log = "";
        for(Server server : servers) {
            Double requestCount = this.serverRequestCountsIn5seconds.get(server).doubleValue()/(5*ENGINE_MAX_REQUEST);
            log += new BigDecimal(requestCount + "").toString() + "   ";
            this.serverRequestCountsIn5seconds.get(server).set(0);
        }
        try {
            this.writerForBusyness.write(log + "\r\n");
            this.writerForBusyness.flush();
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }


    private class UpdateBusynessTask implements  Runnable {
        @Override
        public void run() {
            LBEGSRule.this.updateServerHistoryBusyness();
        }
    }

    private class WriteBusynessTask implements  Runnable {
        @Override
        public void run() {
            LBEGSRule.this.writeBusyness();
        }
    }

}
