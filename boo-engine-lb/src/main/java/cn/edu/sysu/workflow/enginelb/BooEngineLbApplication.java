package cn.edu.sysu.workflow.enginelb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Skye
 * Created on 2020/4/3
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BooEngineLbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooEngineLbApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
