package cn.edu.sysu.workflow.enginefeign;

import cn.edu.sysu.workflow.common.util.IdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Skye
 * Created on 2020/4/3
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BooEngineFeignApplication {

    /**
     * engine feign service micro-service global id.
     */
    public static final String ENGINE_FEIGN_ID = "engine-feign-" + IdUtil.nextId();

    public static void main(String[] args) {
        SpringApplication.run(BooEngineFeignApplication.class, args);
    }

}
