package com.nb.james.springboot.cloud.hystrix;

import com.netflix.hystrix.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by zhangyaping on 2017/8/31.
 */
public class HelloHystrixCommand extends HystrixCommand<String> {

    private String name;

    @Override protected String getFallback() {
        return  name+":"+
                (isResponseShortCircuited()?"isResponseShortCircuited ":"")  + "-" +
                (isResponseSemaphoreRejected()?"isResponseSemaphoreRejected ":"")  + "-" +
                (isResponseThreadPoolRejected()?"isResponseThreadPoolRejected ":"")  + "-" +
                ("duration : " + getExecutionTimeInMilliseconds())  + "-" +
                (isResponseTimedOut()?" timeout":" fallback");
    }

    @Override
    protected String run() throws Exception {
        int number = Integer.parseInt(name.substring(1,name.length()-1));
        long target = Calendar.getInstance().getTimeInMillis()+((number%2)*3)*1000;

        while(/*number % 2 == 0 &&*/ Calendar.getInstance().getTimeInMillis()<target){
            //            System.out.println("wait");
//            target++;
        }
        return "hi "+name;
    }

    public HelloHystrixCommand(String name) {
//        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CircuitBreakerTestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreakerTestKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CircuitBreakerTest"))
                .andThreadPoolPropertiesDefaults(	// 配置线程池
                        HystrixThreadPoolProperties.Setter()
                                // 配置线程池里的线程数，设置足够多线程，以防未熔断却打满threadpool
                                .withCoreSize(200)
                )
                .andCommandPropertiesDefaults(	// 配置熔断器
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerEnabled(true)
                                .withCircuitBreakerRequestVolumeThreshold(1)
                                .withCircuitBreakerErrorThresholdPercentage(70)
                                .withRequestCacheEnabled(false)
//                                .withCircuitBreakerForceOpen(true)	// 置为true时，所有请求都将被拒绝，直接到fallback
//                                .withCircuitBreakerForceClosed(true)	// 置为true时，将忽略错误
                                .withExecutionIsolationStrategy(
                                        HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)	// 信号量隔离
                                .withExecutionTimeoutEnabled(true)
                                .withExecutionTimeoutInMilliseconds(4000)
                )
        );
        this.name = name;
    }


}
