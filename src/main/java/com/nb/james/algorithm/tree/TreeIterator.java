package com.nb.james.algorithm.tree;


import com.google.common.base.Joiner;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by zhangyaping on 2017/3/24.
 */
public class TreeIterator {

    private TreeNode root;
    private static String DEFAULT_TREE_JSON = "{\"value\":11,\"addTime\":\"2017-02-01\",\"left\":{\"value\":22,\"left\":null,\"right\":{\"value\":26,\"left\":null,\"right\":null}},\"right\":{\"value\":33,\"left\":null,\"right\":{\"value\":44,\"left\":null,\"right\":{\"value\":55,\"left\":{\"value\":66,\"left\":null,\"right\":null},\"right\":null}}}}";

    private TreeNode initTree(String... param){
        if(param.length>0)
            DEFAULT_TREE_JSON = param[0];
        root = new TreeNode(DEFAULT_TREE_JSON);
        return root;
    }

    public static void main(String args[]){
        TreeNode tree = new TreeNode(DEFAULT_TREE_JSON);
//        System.out.println(tree.toString());
        traverseForValue(tree);
    }

    public void run(){
        TreeNode tree = new TreeNode(DEFAULT_TREE_JSON);
    }

    /**
     * 借助stack完成对树的遍历（本示例是二叉树的遍历）
     * 1.从root node开始，按照约定，把当前节点下的 X 叶子 node依次加入到栈内并记录当前节点（本示例中随机挑选了左叶子）
     * 2.第一步把从root开始所有左叶子节点入栈后，弹出最后放进来的node
     * 3.从当前node开始，开始遍历他的右叶子node，同样入栈
     * not recursive way
     * @param root
     */
    public static void traverseForValue(TreeNode root){
        TreeNode curNode = root;
        Stack<TreeNode> vStack = new Stack<TreeNode>();
        vStack.push(curNode);
        Set<String> result = new HashSet<>();
        while(vStack.size()>0 && curNode != null){
            while(curNode.getLeft() != null){
                vStack.push(curNode.getLeft());
                curNode = curNode.getLeft();
            }
            curNode = vStack.pop();
            result.add(String.valueOf(curNode.getValue()));

            while(curNode.getRight() != null){
                vStack.push(curNode.getRight());
                curNode = curNode.getRight();
            }
        }
        System.out.format("result:%s", Joiner.on(",").join(result));
    }

}
