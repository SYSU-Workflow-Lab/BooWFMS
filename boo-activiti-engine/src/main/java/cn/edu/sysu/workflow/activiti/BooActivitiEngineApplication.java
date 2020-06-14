package cn.edu.sysu.workflow.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
public class BooActivitiEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooActivitiEngineApplication.class, args);
    }

}
