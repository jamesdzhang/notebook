package com.nb.james.netty.server.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by zhangyaping on 2017/7/12.
 */
public class BroadCastEvent extends ApplicationEvent {

    private BROADCAST_TYPE type;

    public BROADCAST_TYPE getType() {
        return type;
    }

    public BroadCastEvent(Object source, BROADCAST_TYPE type){
        super(source);
        this.type = type;
    }

    public enum BROADCAST_TYPE{
        SHUTDOWN,
        MSG;
    }

}
