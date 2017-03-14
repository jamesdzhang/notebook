package com.nb.james.springboot.service.impl;

import com.nb.james.springboot.service.IService;
import org.springframework.stereotype.Service;

/**
 * Created by zhangyaping on 2017/3/14.
 */
@Service(value = "prototype")
public class ServiceImpl implements IService{

    @Override
    public String hello() {
        return "Wow";
    }
}
