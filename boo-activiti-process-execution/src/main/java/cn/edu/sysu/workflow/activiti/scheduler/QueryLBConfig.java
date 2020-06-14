package cn.edu.sysu.workflow.activiti.scheduler;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 查询请求使用的负载均衡策略
 * @author: Gordan Lin
 * @create: 2019/12/20
 **/
@Configuration
@RibbonClient(name = "boo-query-service", configuration = QueryLBConfig.class)
public class QueryLBConfig {

    @Bean
    public IRule ribbonRule() {
        return new RoundRobinRule();
    }
}