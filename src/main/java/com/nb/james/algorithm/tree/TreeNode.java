package com.nb.james.algorithm.tree;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.json.GsonJsonParser;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by zhangyaping on 2017/3/24.
 */
public class TreeNode {

    private Integer value;
    private TreeNode left;
    private TreeNode right;
    private static final String V_VALUE = "value";
    private static final String V_LEFT = "left";
    private static final String V_RIGHT = "right";

    public TreeNode(String nodeJson){
        GsonJsonParser parser = new GsonJsonParser();
        Map<String, Object> tMap = new WeakHashMap<>();
//        tMap = parser.parseMap(nodeJson);
        try{
            JSONObject obj = new JSONObject(nodeJson);
            Iterator it = obj.keys();
            while(it.hasNext()){
                String curKey = String.valueOf(it.next());
                tMap.put(curKey,obj.get(curKey));
            }
        }catch (Exception e){

        }
        if(null != tMap.get(V_VALUE)){
            this.value = (int)tMap.get(V_VALUE);
            if(null != tMap.get(V_LEFT))
                this.left = new TreeNode(String.valueOf(tMap.get(V_LEFT)));
            if(null != tMap.get(V_RIGHT))
                this.right = new TreeNode(String.valueOf(tMap.get(V_RIGHT)));
        }
    }

    @Override
    public String toString() {
        if(value != null)
            return "["+value+"-"+left+"-"+right+"]";
        else
            return "NULL";
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

}
