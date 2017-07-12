package com.nb.james.netty.server.handler;

import com.nb.james.netty.server.event.BroadCastEvent;
import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.springframework.context.ApplicationListener;

import java.nio.charset.Charset;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter implements ApplicationListener<BroadCastEvent>{ // (1)

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("channelRegistered : "+ ctx.channel().attr(AttributeKey.valueOf("msg")).get());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ByteBuf in = (ByteBuf) msg;//字节
        try {
            /*while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }*/
            byte[] tmp = new byte[in.readableBytes()];
            in.readBytes(tmp);
            System.out.print(new String(tmp,Charset.forName("UTF-8")));
            System.out.println(" from "+ctx.name());
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        ByteBuf str = Unpooled.copiedBuffer("Hello Client".getBytes());
        time.writeCharSequence(ctx.name(), Charset.forName("UTF-8"));
        time.writeLong(System.currentTimeMillis());

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
//                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void onApplicationEvent(BroadCastEvent broadCastEvent) {
        //TODO
        Object obj = broadCastEvent.getSource();
    }
}