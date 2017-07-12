package com.nb.james.springboot.controller;

import com.nb.james.netty.client.TimeClient;
import com.nb.james.spring.mvc.support.annotations.Json;
import com.nb.james.spring.mvc.support.annotations.ResponseJson;
import com.nb.james.spring.mvc.support.dtos.HelloDto;
import com.nb.james.springboot.service.IService;
import com.nb.james.utils.dto.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Executors;

/**
 * Created by zhangyaping on 2017/3/14.
 */
@RestController
@RequestMapping(value = "/springBoot")
public class ControllerDemo {

    private Logger logger = LoggerFactory.getLogger(ControllerDemo.class);

    @Value("${message:\"HelloWorld\" default}")
    private String message;
    @Value("${greeting}")
    private String greeting;

    @Resource
    private TimeClient timeClient;

    @Resource
    private IService service;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello(String param){
        System.out.println("${database.url.a}");
        return greeting + service.hello();
    }

    @RequestMapping(value = "/hello2", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseJson
    public String jsonTest(@Json HelloDto helloDto){
        ResponseVo vo = new ResponseVo();
        vo.setMsg(helloDto.getName());
        return helloDto.getName();
    }

    @RequestMapping(value = "/netty", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseJson
    public String nettyTest(@Json HelloDto helloDto){
        ResponseVo vo = new ResponseVo();
        //connect to netty server
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        timeClient.startClient();
                    }catch (Exception e){
                        logger.info("TimeClient ex :"+e.getMessage());
                    }

                }
            });
        timeClient.startTestThread(null!=helloDto.getAge()?helloDto.getAge():-1);
        return "done";
    }

}
