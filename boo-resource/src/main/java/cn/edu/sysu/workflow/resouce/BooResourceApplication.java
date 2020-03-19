package cn.edu.sysu.workflow.resouce;

import cn.edu.sysu.workflow.common.util.IdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author Skye
 * Created on 2019/9/16
 */
@SpringBootApplication
@EnableScheduling
public class BooResourceApplication {

    /**
     * BOEngine service micro-service global id.
     */
    public static final String RESOURCE_ID = "resource-" + IdUtil.nextId();

    /**
     * Prefix of admin queue.
     */
    public static final String WORK_ITEM_LIST_ADMIN_PREFIX = "admin@";

    public static void main(String[] args) {
        SpringApplication.run(BooResourceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
