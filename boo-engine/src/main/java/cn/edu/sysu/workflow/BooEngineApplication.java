package cn.edu.sysu.workflow;

import cn.edu.sysu.workflow.util.IdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

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

    public static void main(String[] args) {
        SpringApplication.run(BooEngineApplication.class, args);
    }

}
