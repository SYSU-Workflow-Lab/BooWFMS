package cn.edu.sysu.workflow.enginefeign.configuration;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 暂时关闭Hystrix，后面完善熔断、降级等功能
 *
 * @author Skye
 * Created on 2020/5/20
 */
@Configuration
public class DisableHystrixFeignConfiguration {

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}
