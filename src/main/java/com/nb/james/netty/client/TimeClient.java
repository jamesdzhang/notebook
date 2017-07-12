package com.nb.james.netty.client;

import com.nb.james.netty.client.handler.TimeClientHandler;
import com.nb.james.netty.server.event.BroadCastEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class TimeClient{

    Logger logger = LoggerFactory.getLogger(TimeClient.class);

    private static Byte client_conn_status = (byte)0;

    private static final String host = "localhost";
    private static final int port = 9999;

    @Autowired
    private ApplicationContext applicationContext;

    private static Bootstrap bootstrap;

    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Autowired
    private TimeClientHandler timeClientHandler;

    public static void main(String[] args) throws Exception {

//        startClient();
    }

    /**
     * 测试方法，根据参数决定是否关闭连接
     * @param closePotnt
     */
    public void startTestThread(int closePotnt){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i < 10;i++){
                    try{
                        Thread.sleep(3000);
                        BroadCastEvent evt = new BroadCastEvent("第"+i+"次发送广播",
                                i==closePotnt?BroadCastEvent.BROADCAST_TYPE.SHUTDOWN:BroadCastEvent.BROADCAST_TYPE.MSG);
                        applicationContext.publishEvent(evt);
                    }catch (InterruptedException e){
                        //do nothing
                    }
                }
            }
        });
    }


    /**
     * 入口
     * @throws Exception
     */
    public synchronized void startClient() throws Exception{
        if(client_conn_status.equals((byte)1)){
            logger.info("客户端已连接");
            return;
        }

        try {
            if(workerGroup.isShutdown()){
                workerGroup = new NioEventLoopGroup(1,Executors.newSingleThreadExecutor());
                bootstrap = null;//bootstrap 需要重新绑定
            }
            initBootStrap(workerGroup);

            startChannel(bootstrap);
        } finally {
            this.gracefullyShutdown();
        }
    }

    /**
     * 启动连接，记录连接状态
     * @param bootstrap
     * @throws Exception
     */
    private void startChannel(Bootstrap bootstrap) throws Exception{
        // Start the client.
        ChannelFuture f = bootstrap.connect(host, port).sync(); // (5)
        synchronized (client_conn_status){
            client_conn_status = (byte)1;
        }
        // Wait until the connection is closed.
        f.channel().closeFuture().sync();
    }

    /**
     * bootstrap 和 worker group 是绑定在一起的
     * worker group关闭的时候，绑定到的bootstrap肯定是不能继续使用的
     * TODO 可以考虑客户端显式关闭worker group 或者不提供关闭（针对客户端只有一个连接）。
     * @param workerGroup
     */
    private synchronized void initBootStrap(EventLoopGroup workerGroup){
        if(null != bootstrap)
            return;
        bootstrap = new Bootstrap();

        bootstrap.group(workerGroup); // (2)
        bootstrap.channel(NioSocketChannel.class); // (3)
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(timeClientHandler);
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            }
        });
    }

    private void gracefullyShutdown(){
//        workerGroup.shutdownGracefully();
        synchronized (client_conn_status){
            client_conn_status = (byte)0;
        }
    }
}