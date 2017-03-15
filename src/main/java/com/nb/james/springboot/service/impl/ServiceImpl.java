package com.nb.james.springboot.service.impl;

import com.nb.james.netty.server.NettyServer;
import com.nb.james.springboot.service.IService;
import org.springframework.stereotype.Service;

/**
 * Created by zhangyaping on 2017/3/14.
 */
@Service("prototype")
public class ServiceImpl implements IService{

    @Override
    public String hello() {
        //start a netty server
        new NettyServer(8000).run();
        return "\nWow\n".intern();
    }
}
