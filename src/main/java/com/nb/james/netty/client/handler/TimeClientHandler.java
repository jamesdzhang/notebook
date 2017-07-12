package com.nb.james.netty.client.handler;

import com.nb.james.netty.server.event.BroadCastEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Component
@ChannelHandler.Sharable
public class TimeClientHandler extends ChannelInboundHandlerAdapter implements ApplicationListener<BroadCastEvent>{

    private Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private CountDownLatch countDownLatch;

    private ChannelHandlerContext ctx_;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        byte[] tmp = new byte[m.readableBytes()-8];
        m.readBytes(tmp);
        try {
            System.out.println(new String(tmp,Charset.forName("UTF-8")));
            long currentTimeMillis = (m.readLong());
            System.out.println(new Date(currentTimeMillis));
//            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        final ByteBuf sayhi = ctx.alloc().buffer(4); // (2)
        sayhi.writeCharSequence("Hi, This is client [" + ctx.name() + "], I'm on board.", Charset.forName("UTF-8"));
        ctx.writeAndFlush(sayhi);
//        ctx.channel().pipeline().writeAndFlush("Hi, I'm on board.");
        ctx_ = ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info(cause.getMessage());
    }

    @Override
    public void onApplicationEvent(BroadCastEvent broadCastEvent){
        Object obj = broadCastEvent.getSource();
        if(broadCastEvent.getType().equals(BroadCastEvent.BROADCAST_TYPE.MSG))
            sendMsg(ctx_,String.valueOf(obj));
        else if(broadCastEvent.getType().equals(BroadCastEvent.BROADCAST_TYPE.SHUTDOWN)){
            ctx_.close();
            logger.warn(ctx_.name()+" closed");
        }
    }

    private void sendMsg(ChannelHandlerContext ctx, String msg){
        try{
            final ByteBuf sayhi = ctx.alloc().buffer(4); // (2)
            sayhi.writeCharSequence("client [" + ctx.name() + "] report in:"+msg, Charset.forName("UTF-8"));
            ctx.writeAndFlush(sayhi);
        }catch (Exception e){
            logger.error("sendMsg exception:",e.getMessage());
        }
    }
}