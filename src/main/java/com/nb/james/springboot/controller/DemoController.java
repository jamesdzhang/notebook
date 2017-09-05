package com.nb.james.springboot.controller;

import com.nb.james.mybatis.dao.ICdsOrderDao;
import com.nb.james.mybatis.vo.CdsOrderVo;
import com.nb.james.netty.client.TimeClient;
import com.nb.james.spring.mvc.support.annotations.Json;
import com.nb.james.spring.mvc.support.annotations.ResponseJson;
import com.nb.james.spring.mvc.support.dtos.HelloDto;
import com.nb.james.springboot.cloud.hystrix.HelloHystrixCommand;
import com.nb.james.springboot.service.IService;
import com.nb.james.utils.dto.ResponseVo;
import com.nb.james.utils.tools.JsonUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhangyaping on 2017/3/14.
 */
@RestController
@RequestMapping(value = "/springBoot")
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Value("${message:\"HelloWorld\" default}")
    private String message;
    @Value("${greeting}")
    private String greeting;

    @Resource
    private TimeClient timeClient;

    @Resource
    private IService service;
    @Resource
    private ICdsOrderDao cdsOrderDao;

    public final static ExecutorService tpService = Executors.newFixedThreadPool(20);

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello(String param){
        System.out.println("${database.url.a}");
        CdsOrderVo result = cdsOrderDao.selectByOrderId("123");
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


    @RequestMapping(value = "/forward", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseJson
    public String httpClientTest(@Json HelloDto helloDto){
        HostConfiguration hostConfiguration = new HostConfiguration();
        hostConfiguration.setHost("10.253.17.155",9086);
//        hostConfiguration.setHost("10.253.17.155:9086/"
//                + "com.zhongan.castle.support.service.VehiclePolicySearchService:1.0.0/"
//                + "queryPolicyByConditions",9086);

        HttpClientParams params = new HttpClientParams();
        params.setParameter("ArgsTypes","[\"com.zhongan.castle.support.dto.search.VehiclePolicySearchReqDTO\"]"  );
        params.setParameter("ArgsObjects","[{\"accountId\":\"3070013\"}]"  );

        HttpClient httpClient = new HttpClient();
//        httpClient.setHostConfiguration(hostConfiguration);
//        httpClient.setParams(params);

        PostMethod postMethod = new PostMethod("http://10.253.17.155:9086/"
                                + "com.zhongan.castle.support.service.VehiclePolicySearchService:1.0.0/"
                                + "queryPolicyByConditions");
        postMethod.setParams(params);
        NameValuePair[] valuePairs = {new NameValuePair("ArgsTypes",
                "[\"com.zhongan.castle.support.dto.search.VehiclePolicySearchReqDTO\"]"),
                new NameValuePair("ArgsObjects",
                        "[{\"accountId\":\"3070013\"}]")};
        postMethod.setRequestBody(valuePairs);
        String responseBody = null;
        try{
            int reqStatus = httpClient.executeMethod(postMethod);
            logger.info("request status :",reqStatus);
            if(reqStatus == HttpStatus.SC_OK){
                byte[] rsponsBody = postMethod.getResponseBody();
                responseBody = new String(rsponsBody);
            }
        }catch (Exception e){
            logger.error("exception :",e);
        }finally {
            postMethod.releaseConnection();
        }

        return JsonUtil.toJsonString(responseBody);
    }



    @RequestMapping(value = "/hystrix", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseJson
    public String hystrixTest(@Json HelloDto helloDto){

        List<Future<String>> list = new ArrayList<Future<String>>();
        CountDownLatch counter = new CountDownLatch(20);
        StringBuilder result = new StringBuilder();
        for(int i=0; i<20 ;i++){
            final int name = i;

            HelloHystrixCommand helloHystrixCommand = new HelloHystrixCommand("["+name+"]");
            try{
                //                list.add(helloHystrixCommand.queue());
                //                log.info(helloHystrixCommand.execute());
            }catch (Exception e){
                logger.error("Exception :", e);
            }
            tpService.execute(new Runnable() {
                @Override public void run() {
//                    logger.info(helloHystrixCommand.execute());
                    result.append(helloHystrixCommand.execute()).append("\n");
                    counter.countDown();
                }
            });
        }

        //        for(int i=0; i<20 ;i++){
        //
        //            try{
        //                log.info(list.get(i).get());
        //            }catch (Exception e){
        //                log.error("Exception :", e);
        //            }
        //        }

        try{
            counter.await();
        }catch(InterruptedException e){
            logger.error("exception :",e);
        }
        return result.toString();
    }

}
