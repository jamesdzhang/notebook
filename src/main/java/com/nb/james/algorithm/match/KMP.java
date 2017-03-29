package com.nb.james.algorithm.match;


import java.util.*;

/**
 * Created by zhangyaping on 2017/3/27.
 */
public class KMP {

    public static int indexOf(char[] target, char[] match){
        int index = -1; //-1代表不存在
        int targetLength = target.length;
        int matchLength = match.length;
        int next[] = initNext(match);
        int i = 0,j = 0;
        while(i<targetLength && j<matchLength){
            if(j == -1 || target[i] == match[j]){
                i++;
                j++;
            }else{
                /*i = i-j+1;
                j=0;*/ //暴力回退到匹配字符的第一个位置,非KMP算法实现
                j = next[j];
            }
        }
        if(j==matchLength){
            return i-j;
        }
        return index;
    }

    /**
     * KMP核心
     * 计算出来匹配字符每一个位置上如果匹配失败应该回退到的最优位置（原始方式是强制从第一位置重新匹配）。
     * 例如 abab  匹配到第三个位置失败其实匹配位置无需重新匹配第一个位置，只要匹配第一个位置即可。
     * @param match
     * @return
     */
    private static int[] initNext(char[] match){
        int[] next = new int[match.length];
        int pLen = match.length;
        next[0] = -1;
        int k = -1;
        int j = 0;
        System.out.println("next[0]:"+-1);
        while (j < pLen - 1) {
            //p[k]表示前缀，p[j]表示后缀
            if (k == -1 || match[j] == match[k]) {
                ++k;
                ++j;
                next[j] = k;
            } else {
                k = next[k];
            }
        }

        System.out.println("next数组："+Arrays.toString(next));
        return next;
    }

    public static void main(String args[]){
        char[] target = "sasbabgjdabsacgdavababababcogsdfjdkljsdewef".toCharArray();
        char[] match = "ababc".toCharArray();
        int index = indexOf(target,match);
        System.out.println("【"+String.valueOf(match)+"】在"+"【"+String.valueOf(target)+"】"+"位置为"+index);
    }

}
