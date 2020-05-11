package cn.edu.sysu.workflow.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Skye
 * Created on 2020/5/11
 */

@EnableEurekaServer
@SpringBootApplication
public class BooEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooEurekaServerApplication.class, args);
    }

}
