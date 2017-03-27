package com.nb.james.springboot.main;

import com.nb.james.springboot.configuration.ConfigurationDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpRequest;

import java.util.Arrays;

/**
 * Created by zhangyaping on 2017/3/14.
 */

@SpringBootApplication
public class SpringBootMain {

    public static void main(final String args[]){
        ConfigurableApplicationContext context = SpringApplication.run(ConfigurationDemo.class, args);
        String env = context.getEnvironment().getProperty("env");
        System.out.println(env);
//        context.register(ConfigurationDemo.class);
//        context.register(PrdProfile.class);
//        context.refresh();
//        System.out.println(context.getEnvironment().getActiveProfiles());
    }

}
