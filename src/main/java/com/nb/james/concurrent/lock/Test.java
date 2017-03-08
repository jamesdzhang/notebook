package com.nb.james.concurrent.lock;


import com.nb.james.concurrent.lock.thread.ReadThread;
import com.nb.james.concurrent.lock.thread.WriteThread;

/**
 * Created by zhangyaping on 2017/1/22.
 */
public class Test {

    public static void main(String args[]){
        Data data = new Data(10);

        new ReadThread(data).start();
        new ReadThread(data).start();
        new ReadThread(data).start();
        new ReadThread(data).start();
        new ReadThread(data).start();

        new WriteThread(data,"AAAAAAAAAA").start();
        new WriteThread(data,"BBBBBBBBBB").start();
        new WriteThread(data,"1111111111").start();


    }
}
