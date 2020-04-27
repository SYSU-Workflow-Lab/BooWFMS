package cn.edu.sysu.workflow.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Skye
 * Created on 2019/9/16
 */
@EnableZuulProxy
@SpringBootApplication
public class BooAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooAccessApplication.class, args);
    }

}
