package com.nb.james.springboot.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by zhangyaping on 2017/3/14.
 */
@SpringBootConfiguration
//@EnableWebMvc
@ComponentScan(value = "com.nb.james.*")
@ImportResource({"application-start.xml"})
public class ConfigurationDemo {

    /**
     * 禁用spring boot默认的一个filter，因为他会先于我们自定义的filter取request的参数
     * he is out!
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(HiddenHttpMethodFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }


}
