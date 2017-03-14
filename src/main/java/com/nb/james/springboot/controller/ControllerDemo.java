package com.nb.james.springboot.controller;

import com.nb.james.springboot.service.IService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource
    private IService service;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello(String param){
        return greeting + service.hello();
    }

}
