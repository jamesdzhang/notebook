package com.nb.james.spring.mvc.support.dtos;

/**
 * Created by zhangyaping on 2017/4/24.
 */
public class HelloDto {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    String name;
    Integer age;

}
