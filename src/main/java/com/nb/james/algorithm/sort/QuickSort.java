package com.nb.james.algorithm.sort;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

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
     */
    public static void quick_sort(Integer[] unsorted, int low, int high){
        int loc = 0;
        if (low < high){
            loc = partition(unsorted, low, high);
            quick_sort(unsorted, low, loc - 1);
            quick_sort(unsorted, loc + 1, high);
        }

    }

    public static Integer partition(Integer[] unsorted, int low, int high){
        int pivot = unsorted[low];
        while (low < high) {
            while (low < high && unsorted[high] > pivot)
                high--;
            unsorted[low] = unsorted[high];
            while (low < high && unsorted[low] <= pivot)
                low++;
            unsorted[high] = unsorted[low];
        }
        unsorted[low] = pivot;
        System.out.println(String.format("loc:%d",low));
        return low;
    }


    /**
     * 数组实现
     * @param unsorted
     */
    public static void quick_sort_queue(Integer[] unsorted){
        int loc = 0;
        Queue<Pos> queue = new LinkedBlockingQueue<>();
        queue.add(new Pos(0,unsorted.length-1));
        Pos tmp;
        while ((tmp = queue.poll()) != null) {
            int low = tmp.low;
            int high = tmp.high;
            int low_ = low;
            int high_ = high;
            int pivot = unsorted[low];
            while (low < high) {
                while (low < high && unsorted[high] > pivot)
                    high--;
                unsorted[low] = unsorted[high];
                while (low < high && unsorted[low] <= pivot)
                    low++;
                unsorted[high] = unsorted[low];
            }
            unsorted[low] = pivot;
            loc = low;
            if (low_ < loc - 1)
                queue.add(new Pos(low_, loc - 1));
            if (loc + 1 < high_)
                queue.add(new Pos(loc + 1, high_));
        }
    }

    public static void main(String args[]){
        Stopwatch sWatch;
        Integer[] array = {1,111,88,10,31,17,101,31,2,32,45,30,11,22,23,223,22,11,7,5};
        Integer array_2[] = array;
        Integer array_3[] = array;
        System.out.println(String.format("Pending sorting : %s", Joiner.on(",").join(array)));

        sWatch = Stopwatch.createStarted();

        sWatch = sWatch.reset().start();
        quick_sort(array_2, 0, array.length - 1);
        System.out.println(String.format("After Sort: %s", Joiner.on(",").join(array_2)));
        System.out.println(String.format("Time consumed : %d MICROSECONDS of %s", sWatch.stop().elapsed(MICROSECONDS), "vector"));


        sWatch = sWatch.reset().start();
        quick_sort_queue(array_3);
        System.out.println(String.format("After Sort: %s", Joiner.on(",").join(array_3)));
        System.out.println(String.format("Time consumed : %d MICROSECONDS of %s", sWatch.stop().elapsed(MICROSECONDS), "queue"));
    }

}

class Pos {
    public int low;
    public int high;

    Pos(int low, int high) {
        this.low = low;
        this.high = high;
    }
}
