package cn.edu.sysu.workflow.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Skye
 * Created on 2019/9/16
 */
@EnableZuulProxy
@SpringBootApplication
@EnableRedisHttpSession
public class BooAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooAccessApplication.class, args);
    }

}
