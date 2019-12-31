package cn.edu.sysu.workflow.engine;

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
public class BooEngineApplication {

    /**
     * BOEngine service micro-service global id.
     */
    public static final String ENGINE_ID = "engine-" + IdUtil.nextId();

    /**
     * false:read from database
     * true:read local BO, no any interaction
     */
    public static boolean IS_LOCAL_DEBUG = false;

    public static void main(String[] args) {
        SpringApplication.run(BooEngineApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
