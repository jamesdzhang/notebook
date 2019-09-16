package com.nb.james.algorithm.sort;

import java.util.Arrays;

/**
 * Combination of bubble sort and insertion sort.
 * Created by zhangyaping on 2017/3/17.
 */
public class ShellSort {

    /**
     *
     * @param queue
     */
    public static void shellSort(int[] queue){
        int groupGap = queue.length;
        int temp;
        while(true){
            groupGap =(int)Math.ceil(groupGap/4);
            for(int x=0;x<groupGap;x++){
                for(int i = x+groupGap;i<queue.length;i+=groupGap){
                    int j=i-groupGap;
                    temp=queue[i];
                    for(;j>=0 && temp < queue[j];j -= groupGap){
                        queue[j+groupGap]=queue[j];
                    }
                    queue[j+groupGap]=temp;
                    if(i+1==queue.length)
                        System.out.println("i - j: "+ i + "-" +j);
                }
                System.out.println("x: "+ x);
            }
            if(groupGap==1)
                break;
        }
        System.out.println(Arrays.toString(queue));
    }

    public static void main(String args[]){
        int[] a = {31,2,45,30,11,22,23};
        ShellSort.shellSort(a);
    }
}
