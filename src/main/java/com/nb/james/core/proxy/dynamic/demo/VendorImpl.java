package com.nb.james.core.proxy.dynamic.demo;

import com.nb.james.core.proxy.dynamic.demo.Sale;

/**
 * Created by zhangyaping on 2017/3/29.
 */
public class VendorImpl implements Sale {

    @Override
    public void sayHi(String name){
        System.out.println("vendor says hi,"+name);
    }

}
