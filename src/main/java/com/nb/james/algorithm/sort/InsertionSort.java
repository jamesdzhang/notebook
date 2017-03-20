package com.nb.james.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangyaping on 2017/3/17.
 */
public class InsertionSort {

    /**
     *  插入排序：
     * @param queue
     */
    public static List<Integer> quickSort(List<Integer> queue){
        int rootIndex = new Random().nextInt(queue.size()-1);

        return partition(queue,rootIndex);

    }

    public static List<Integer> partition(List<Integer> aList, int index){
        return null;
    }

    public static void main(String args[]){
        Integer[] a = {1,1,31,2,32,45,30,11,22,23};
        List<Integer> list = Arrays.asList(a);
        System.out.println(Arrays.toString(InsertionSort.quickSort(list).toArray()));
    }

}
