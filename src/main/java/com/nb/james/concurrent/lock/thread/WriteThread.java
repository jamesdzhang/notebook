package com.nb.james.concurrent.lock.thread;


import com.nb.james.concurrent.lock.Data;

/**
 * Created by zhangyaping on 2017/1/22.
 */
public class WriteThread extends Thread {

    private final Data data;
    private final String str;
    private int index = 0;

    public WriteThread(Data data,String str){
        this.data = data;
        this.str = str;
    }

    public void run(){
        while(true){
            long startMs = System.currentTimeMillis();
            try{
                char c = next();
                data.write(c);
            }catch (InterruptedException e){

            }
            long costTime = System.currentTimeMillis()-startMs;
            System.out.println(Thread.currentThread().getName()+" [Write] cost :"+costTime);
        }
    }


    private char next(){
        char c= str.charAt(index);
        index++;
        if(index>=str.length()){
            index = 0;
        }
        return c;
    }

}
