package com.nb.james.core.proxy.dynamic;

import com.nb.james.core.proxy.dynamic.demo.Sale;
import com.nb.james.core.proxy.dynamic.demo.VendorImpl;

/**
 * Created by zhangyaping on 2017/3/29.
 */
public class ProxyMain {

    public static void main(String args[]){
//        Proxy ab = new Proxy(new Vendor());
        DynamicProxy dynamicProxy = new DynamicProxy(new VendorImpl());
        try{
            args = new String[1];
            args[0] = "customer";
            dynamicProxy.invoke(dynamicProxy,Sale.class.getDeclaredMethods()[0],args);

//            Sale sale = (Sale)(Proxy.newProxyInstance(Sale.class.getClassLoader(),
//                    new Class[]{Sale.class},dynamicProxy));
//            sale.sayHi(args[0]);
        }catch (Throwable e){
            System.out.println(e.getMessage());
        }

    }

}
