package com.nb.james.algorithm;

import java.util.Arrays;

/**
 * Created by zhangyaping on 2017/3/17.
 */
public class ShellSort {

    public static void shellSort(int[] a){
        double length = a.length;
        int temp=0;
        while(true){
            length = Math.ceil(length/2);
            int groupLenth =(int)length;
            for(int x=0;x<groupLenth;x++){
                for(int i = x+groupLenth;i<a.length;i+=groupLenth){
                    int j=i-groupLenth;
                    temp=a[i];
                    for(;j>=0&&temp<a[j];j-=groupLenth){
                        a[j+groupLenth]=a[j];
                    }
                    a[j+groupLenth]=temp;
                }
            }
            if(groupLenth==1)
                break;
        }
        System.out.println(Arrays.toString(a));
    }

    public static void main(String args[]){
        int[] a = {31,2,45,30,11,22,23};
        ShellSort.shellSort(a);
    }
}
