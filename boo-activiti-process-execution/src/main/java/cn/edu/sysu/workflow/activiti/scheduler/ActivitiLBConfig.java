package cn.edu.sysu.workflow.activiti.scheduler;

import cn.edu.sysu.workflow.activiti.scheduler.rule.LBEGSRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 流程驱动请求使用的负载均衡策略
 * @author: Gordan Lin
 * @create: 2019/12/20
 **/
@Configuration
@RibbonClient(name = "boo-activiti-engine", configuration = ActivitiLBConfig.class)
public class ActivitiLBConfig {

    @Bean
    public IRule ribbonRule() {
//        return new LBEGSRule();
        return new RandomRule();
    }
}
