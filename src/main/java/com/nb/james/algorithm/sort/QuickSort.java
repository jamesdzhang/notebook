package com.nb.james.algorithm.sort;

import java.util.*;

/**
 * Created by zhangyaping on 2017/3/17.
 */
public class QuickSort {

    /**
     *  快速排序：
     *  1.选取一个任意位置的值作为分组的index
     *  2.按照这个index值把list分为两部分，小于index的都存到index的左边，大于的都存到右边
     *  3.分别递归左边的以及右边的子集合
     *  4.返回最后的集合
     * @param queue
     */
    public static List<Integer> quickSort(List<Integer> queue){
        int rootIndex = new Random().nextInt(queue.size()-1);

        return partition(queue,rootIndex);

    }

    public static List<Integer> partition(List<Integer> aList, int index){
        List<Integer> left = new ArrayList<>(aList.size());
        List<Integer> right = new ArrayList<>(aList.size());
        int indexValue = aList.get(index);
        for(int i = 0; i< aList.size(); i++){
            if(aList.get(i)>indexValue)
                right.add(aList.get(i));
            else if(aList.get(i)<=indexValue && i!=index)
                left.add(aList.get(i));
        }
        if(left.size()>1)
            left = partition(left,new Random().nextInt(left.size()-1));
        if(right.size()>1)
            right = partition(right,new Random().nextInt(right.size()-1));

        left.add(left.size(),indexValue);
        left.addAll(right);

        return left;
    }

    public static void main(String args[]){
        Integer[] a = {1,1,31,2,32,45,30,11,22,23};
        List<Integer> list = Arrays.asList(a);
        System.out.println(Arrays.toString(QuickSort.quickSort(list).toArray()));
    }

}
