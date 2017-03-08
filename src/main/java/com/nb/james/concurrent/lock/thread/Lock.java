package com.nb.james.concurrent.lock.thread;

/**
 * Created by zhangyaping on 2017/1/22.
 */
public class Lock {

    private int readThreadCount = 0; //正在读取的线程数
    private int writeThreadCount = 0;//正在写入的线程数
    private int waitingWriteCount = 0;//等待写入的线程数
    private boolean writeFlag = true;//可写入标记，默认true

    public synchronized void readLock() throws InterruptedException {
        try{
            //有正在写入的线程则读等待
            while(writeThreadCount>0 || (writeFlag && waitingWriteCount>0)){
                wait();
            }
        }finally {
            //读取线程数加1
            readThreadCount++;
        }
    }
    public synchronized void readUnlock(){
        //读取完毕 读取线程数减1
        readThreadCount--;
        writeFlag = true;
        notifyAll();
    }
    public synchronized void writeLock() throws InterruptedException {
        try{
            //先计入写等待线程
            waitingWriteCount++;
            //如果有读线程 或者 写线程在工作 则当前锁等待
            while(readThreadCount>0 || writeThreadCount>0){
                wait();
            }
        }catch (InterruptedException e){

        }finally {
            waitingWriteCount--;
            writeThreadCount++;
        }
    }
    public synchronized void writeUnlock(){
        writeThreadCount--;
        writeFlag = false;
        notifyAll();
    }
}
