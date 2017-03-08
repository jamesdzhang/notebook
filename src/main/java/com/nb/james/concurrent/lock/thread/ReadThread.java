package com.nb.james.concurrent.lock.thread;


import com.nb.james.concurrent.lock.Data;

/**
 * Created by zhangyaping on 2017/1/22.
 */
public class ReadThread extends Thread {

    private final Data data;

    public ReadThread(Data data){
        this.data = data;
    }

    public void run2(){
        while(true){
            long startMs = System.currentTimeMillis();
            for(int i=0; i<10; i++){
                try{
                    String result = data.read();
                    System.out.println(Thread.currentThread().getName()+":"+result);
                }catch (InterruptedException e){

                }
            }
            long costTime = System.currentTimeMillis()-startMs;
            System.out.println(Thread.currentThread().getName()+" [Read] cost :"+costTime);
        }
    }

    @Override
    public void run(){
        while(true){
            long startMs = System.currentTimeMillis();
            try{
//                for(int i=0; i<10; i++){
                    StringBuffer result= new StringBuffer();
                    result.append(data.read());
                    System.out.println(Thread.currentThread().getName()+":"+result);
//                }
            }catch (InterruptedException e){

            }
            long costTime = System.currentTimeMillis()-startMs;
            System.out.println(Thread.currentThread().getName()+" [Read] cost :"+costTime);
        }
    }

}
