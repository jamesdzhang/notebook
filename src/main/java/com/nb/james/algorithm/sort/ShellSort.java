package com.nb.james.algorithm.sort;

import java.util.Arrays;

/**
 * Created by zhangyaping on 2017/3/17.
 */
public class ShellSort {

    /**
     *
     * @param queue
     */
    public static void shellSort(int[] queue){
        double length = queue.length;
        int temp=0;
        while(true){
            length = Math.ceil(length/4);
            int groupLength =(int)length;
            for(int x=0;x<groupLength;x++){
                for(int i = x+groupLength;i<queue.length;i+=groupLength){
                    int j=i-groupLength;
                    temp=queue[i];
                    for(;j>=0 && temp < queue[j];j -= groupLength){
                        queue[j+groupLength]=queue[j];
                    }
                    queue[j+groupLength]=temp;
                    if(i+1==queue.length)
                        System.out.println("i - j: "+ i + "-" +j);
                }
                System.out.println("x: "+ x);
            }
            if(groupLength==1)
                break;
        }
        System.out.println(Arrays.toString(queue));
    }

    public static void main(String args[]){
        int[] a = {31,2,45,30,11,22,23};
        ShellSort.shellSort(a);
    }
}
