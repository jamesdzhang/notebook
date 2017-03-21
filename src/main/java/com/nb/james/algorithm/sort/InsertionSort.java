package com.nb.james.algorithm.sort;

import java.util.*;

/**
 * Created by zhangyaping on 2017/3/17.
 */
public class InsertionSort {

    /**
     *  插入排序：
     *  一个无序列表插入到一个有序列表中
     * @param queue
     */
    public static List<Integer> insertionSort(List<Integer> queue){
        List<Integer> sortedQueue = new LinkedList<Integer>();
        sortedQueue.add(queue.get(0)); // 初始的时候把第一个值加入到有序列表中
        for(Integer value : queue){
            for(int index=0; index<sortedQueue.size(); index++){
                if(sortedQueue.get(index) < value && index+1 ==sortedQueue.size()){
                    sortedQueue.add(value);
                    break;
                }else if(index+1 <= sortedQueue.size() && sortedQueue.get(index) < value && sortedQueue.get(index+1) >= value){
                    sortedQueue.add(index+1,value);
                    break;
                }else if(sortedQueue.get(index) > value){
                    sortedQueue.add(index+1,value);
                    break;
                }
            }
        }
        return sortedQueue;
        /*Integer[] list = (Integer[])queue.toArray();
        for(int index=1; index<list.length; index++){
            if(list[index-1] > list[index]){
                int tmp = list[index];
                int future = index;
                while(future>0 && list[future-1] > tmp){//移动大的数值到左边小于等同于他的位置
                    list[future] = list[future-1];
                    future--;
                }
                list[future] = tmp;//这个位置是list[index](小值)合适的位置
            }
        }
        return Arrays.asList(list);*/
    }

    public static void main(String args[]){
        Integer[] a = {1,111,31,2,32,45,30,11,22,23,223,22,11,7,5};
        List<Integer> list = Arrays.asList(a);
        long start = System.currentTimeMillis();
        System.out.println(Arrays.toString(InsertionSort.insertionSort(list).toArray()));
        System.out.println(System.currentTimeMillis() - start);
    }

}
