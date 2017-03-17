package com.nb.james.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private CountDownLatch countDownLatch;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        byte[] tmp = new byte[m.readableBytes()-4];
        m.readBytes(tmp);
        try {
            System.out.println(new String(tmp,Charset.forName("UTF-8")));
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}