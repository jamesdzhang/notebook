package com.nb.james.springboot.main;

import com.nb.james.springboot.configuration.ConfigurationDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zhangyaping on 2017/3/14.
 */

@SpringBootApplication
public class SpringBootMain {

    public static void main(final String args[]){
        SpringApplication.run(ConfigurationDemo.class, args);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.getEnvironment().setActiveProfiles("prd");
//        context.register(ConfigurationDemo.class);
//        context.register(PrdProfile.class);
//        context.refresh();
//        System.out.println(context.getEnvironment().getActiveProfiles());
    }

}
