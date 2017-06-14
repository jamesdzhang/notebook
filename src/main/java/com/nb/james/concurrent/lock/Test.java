package com.nb.james.concurrent.lock;


import com.nb.james.concurrent.lock.thread.ReadThread;
import com.nb.james.concurrent.lock.thread.WriteThread;
import com.nb.james.springboot.service.IService;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangyaping on 2017/1/22.
 */
public class Test {

    public Test(){
        throw new AssertionError();
    }

    public static void main(String args[]) throws Exception{
        Data data = new Data(10);

//        new ReadThread(data).start();
//        new ReadThread(data).start();
//        new ReadThread(data).start();
//        new ReadThread(data).start();
//        new ReadThread(data).start();
//
//        new WriteThread(data,"AAAAAAAAAA").start();
//        new WriteThread(data,"BBBBBBBBBB").start();
//        new WriteThread(data,"1111111111").start();
        Class<?> test = HttpServlet.class;
        System.out.println(Arrays.toString(getAllAssignedClass(Servlet.class).toArray()));
    }

    @Override
    public void finalize(){

    }

    public static List<Class<?>> getAllAssignedClass(Class<?> cls) throws IOException,
            ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (Class<?> c : getClassesInProjectPath()) {
            if (cls.isAssignableFrom(c) && !cls.equals(c)) {
                classes.add(c);
            }
        }
        return classes;
    }


    /**
     * 取得当前类路径下的所有类
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getClassesInProjectPath() throws IOException,
            ClassNotFoundException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(""); //整个项目包
        String pakPath = classloader.getResource("").getPath();
        return retrieveClasses(new File(url.getFile()), pakPath);
    }

    private static List<Class<?>> retrieveClasses(File dir,String pakPath) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!dir.exists()) {
            return classes;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(retrieveClasses(f, pakPath));
            }
            String name = f.getName();
            if (name.endsWith(".class")) {
                String path = f.getPath().substring(pakPath.length()-1).replace("\\",".");
                path.replace("/",".");
                classes.add(Class.forName(path.substring(0, path.length() - 6)));
            }
        }
        return classes;
    }
}
