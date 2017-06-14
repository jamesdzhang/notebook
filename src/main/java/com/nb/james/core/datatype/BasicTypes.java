package com.nb.james.core.datatype;

/**
 * Created by zhangyaping on 2017/2/24.
 */
public class BasicTypes {

    private int aInt;
    private long aLong;
    private byte aByte;
    private char aChar;
    private boolean aBoolean;
    private double aDouble;
    private short aShort;
    private float aFloat;

    public void int_(){
        String typeName = "int";
        System.out.println("default value of "+typeName+" :"+aInt);
    }

    public void long_(){
        String typeName = "long";
        System.out.println("default value of "+typeName+" :"+aLong);
    }

    public void byte_(){
        String typeName = "byte";
        System.out.println("default value of "+typeName+" :"+aByte);
    }

    public void char_(){
        String typeName = "char";
        System.out.println("default value of "+typeName+" :"+aChar);
    }

    public void boolean_(){
        String typeName = "boolean";
        System.out.println("default value of "+typeName+" :"+aBoolean);
    }

    public void double_(){
        String typeName = "double";
        System.out.println("default value of "+typeName+" :"+aDouble);
    }

    public void short_(){
        String typeName = "short";
        System.out.println("default value of "+typeName+" :"+aShort);
    }

    public void float_(){
        String typeName = "float";
        System.out.println("default value of "+typeName+" :"+aFloat);
    }

    public static void main(String args[]){
        BasicTypes bt = new BasicTypes();
        bt.int_();
        bt.long_();
        bt.boolean_();
        bt.short_();
        bt.float_();
        bt.char_();
        bt.double_();
        bt.byte_();
    }

}
