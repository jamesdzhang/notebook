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
//                i = i-j+1;
//                j=0; //暴力回退到匹配字符的第一个位置
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
     * 计算出来匹配字符每一个位置上如果匹配失败应该回退到的最优位置（原始方式是强制从第一位置重新匹配s）。
     * @param match
     * @return
     */
    private static int[] initNext(char[] match){
        int[] next = new int[match.length];
        next[0] = -1;//第一个位置建议移动到-1的位置
//        Set<Character> set = new HashSet<>();
//        set.add(Character.valueOf(match[0]));
        Map<Character,Integer> nextMap = new WeakHashMap<>(match.length);
        nextMap.put(match[0],1);
        for(int i=1; i<match.length;i++){
            if(nextMap.containsKey(match[i])){
                int charCount = nextMap.get(match[i]);
                nextMap.put(match[i],charCount+1);
                next[i] = charCount;
            }else{
                nextMap.put(match[i],1);
                next[i] = 0;
            }
            System.out.println("next["+i+"] : "+next[i]);
        }
        return next;
    }

    public static void main(String args[]){
        char[] target = "sasbabgjdabsacgdavababababcogsdfjdkljsdewef".toCharArray();
        char[] match = "ababc".toCharArray();
        int index = indexOf(target,match);
        System.out.println("【"+String.valueOf(match)+"】在"+"【"+String.valueOf(target)+"】"+"位置为"+index);
    }

}
