package com.nb.james.concurrent.lock;


import com.nb.james.concurrent.lock.thread.Lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhangyaping on 2017/1/22.
 */
public class Data {

    private final char[] buffer;

    private Lock lock = new Lock();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final java.util.concurrent.locks.Lock readLock = readWriteLock.readLock();
    private final java.util.concurrent.locks.Lock writeLock = readWriteLock.writeLock();

    public Data(int size){
        this.buffer = new char[size];
        for(int i = 0; i<size; i++){
            buffer[i] = '*';
        }
    }

    public String doRead(){
        StringBuffer s = new StringBuffer();
        for(char c : buffer){
            s.append(c);
        }
        sleep(100);
        return s.toString();
    }

    public String read() throws InterruptedException {
        try{
//            lock.readLock();
            readLock.lock();
            return doRead();
        }finally {
//            lock.readUnlock();
            readLock.unlock();
        }
    }

    public  void doWrite(char c){
        StringBuffer s = new StringBuffer();
        for(int i = 0; i<buffer.length; i++){
            buffer[i] = c;
            sleep(100);
        }
    }

    public void write(char c) throws InterruptedException {
        try{
//            lock.writeLock();
            writeLock.lock();
            doWrite(c);
        }finally {
//            lock.writeUnlock();
            writeLock.unlock();
        }
    }

    private void sleep(long ms){
        try{
            Thread.sleep(ms);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
