package com.nb.james.core.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhangyaping on 2017/3/29.
 */
public class DynamicProxy implements InvocationHandler{

    private Object obj;

    public DynamicProxy(Object obj){
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("DynamicProxy before invoke");
        Object result = method.invoke(obj,args);
        System.out.println("DynamicProxy after invoke");
        return result;
    }
}
