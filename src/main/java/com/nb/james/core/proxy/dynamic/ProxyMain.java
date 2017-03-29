package com.nb.james.core.proxy.dynamic;

import com.nb.james.core.proxy.dynamic.demo.Sale;
import com.nb.james.core.proxy.dynamic.demo.VendorImpl;

import java.lang.reflect.Proxy;

/**
 * 代理 - 隐藏委托类的实现  -》 实现客户与委托类的解耦
 * 静态代理类：仅仅是重复劳动，委托类每个方法都在静态代理类有体现
 * 动态代理类：实现invokableHandler接口,供调用具体方法，统一在这一个方法体对委托类方法做处理。
 * Created by zhangyaping on 2017/3/29.
 */
public class ProxyMain {

    public static void main(String args[]){
        DynamicProxy vendorProxy = new DynamicProxy(new VendorImpl());
        try{
            args = new String[1];
            args[0] = "customer";
            //方式一：直接调用代理类的invoke方法
            System.out.println("方式一：直接调用代理类的invoke方法");
            vendorProxy.invoke(vendorProxy,Sale.class.getDeclaredMethods()[0],args);
            System.out.println("===================split================");
            //方式二：根据代理类委托生成代理包装后的实现类（代理类逻辑+委托类逻辑，AOP实现）
            System.out.println("方式二：根据代理类委托生成代理包装后的实现类（代理类逻辑+委托类逻辑，AOP实现）");
            Sale sale = (Sale)(Proxy.newProxyInstance(Sale.class.getClassLoader(),
                    new Class[]{Sale.class},vendorProxy));
            sale.sayHi(args[0]);
        }catch (Throwable e){
            System.out.println(e.getMessage());
        }

    }

}
