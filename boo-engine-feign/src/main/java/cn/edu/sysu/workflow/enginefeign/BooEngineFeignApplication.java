package cn.edu.sysu.workflow.enginefeign;

import cn.edu.sysu.workflow.common.util.IdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Skye
 * Created on 2020/4/3
 */
@SpringBootApplication
public class BooEngineFeignApplication {

    /**
     * engine feign service micro-service global id.
     */
    public static final String ENGINE_FEIGN_ID = "engine-feign-" + IdUtil.nextId();

    public static void main(String[] args) {
        SpringApplication.run(BooEngineFeignApplication.class, args);
    }

}
