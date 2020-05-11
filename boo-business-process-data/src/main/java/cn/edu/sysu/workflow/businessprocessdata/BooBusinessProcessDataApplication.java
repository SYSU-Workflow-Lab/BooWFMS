package cn.edu.sysu.workflow.businessprocessdata;

import cn.edu.sysu.workflow.common.util.IdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author Skye
 * Created on 2020/4/17
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class BooBusinessProcessDataApplication {

    /**
     * business process data service micro-service global id.
     */
    public static final String BUSINESS_PROCESS_DATA_ID = "business-process-data-" + IdUtil.nextId();

    public static void main(String[] args) {
        SpringApplication.run(BooBusinessProcessDataApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
