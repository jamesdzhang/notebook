package com.nb.james.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


/**
 * Created by zhangyaping on 2017/3/14.
 */
@EnableAspectJAutoProxy
@Aspect
@Component
public class AopDemo {

    @Pointcut("execution(* com.nb.james.springboot.service.IService.hello(..))")
    public void pointCutDemo() {}


    @Before("pointCutDemo()")
    public void before(JoinPoint joinPoint) {
        //TODO 调用前操作（权限检查，验证）
        System.out.println("切入前，参数："+ String.valueOf(joinPoint.getArgs()));
    }

    @After("pointCutDemo()")
    public void after(JoinPoint joinPoint) {
        //TODO 调用后返回前操作
    }

    @Around("pointCutDemo()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        //Around等于是综合所有别的切入点
        Object[] objects = point.getArgs();
        //TODO 逻辑处理
        Object result = point.proceed(objects);
        return result;
    }

    @AfterReturning(pointcut = "pointCutDemo()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
        //TODO 返回后操作(日志)
        System.out.println("切入后，返回值："+ String.valueOf(returnVal));
    }

    @AfterThrowing(pointcut = "pointCutDemo()", throwing = "error")
    public void afterThrowing(JoinPoint joinPoint, Throwable error) {
        //TODO 异常后
        System.out.println("异常后，返回值："+ error.getMessage());
    }


}
