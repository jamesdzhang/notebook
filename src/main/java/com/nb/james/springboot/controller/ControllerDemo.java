package com.nb.james.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangyaping on 2017/3/14.
 */
@RestController
@RequestMapping(value = "/springBoot")
public class ControllerDemo {

    @Value("${message:\"HelloWorld\" default}")
    private String message;
    @Value("${greeting}")
    private String greeting;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello(String param){
        return greeting + param;
    }

}
