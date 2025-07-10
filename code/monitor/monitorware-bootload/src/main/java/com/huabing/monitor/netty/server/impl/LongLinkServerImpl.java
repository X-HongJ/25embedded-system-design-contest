package com.huabing.monitor.netty.server.impl;

import com.huabing.monitor.netty.handler.LongLinkChannelHandler;
import com.huabing.monitor.netty.server.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.huabing.monitor.common.utils.SystemUtils.isWindows;

/**
 * @author ping
 * @classname NettyServer
 * @description TODO
 * @date 2019/7/3 10:26
 */
@Component("LongLinkServerImpl")
public class LongLinkServerImpl implements NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongLinkServerImpl.class);

    @Value("${netty.server.port}")
    private int port;

    private EventLoopGroup boss;
    private EventLoopGroup work;

    @Autowired private LongLinkChannelHandler longLinkChannelHandler;

    //    public NettyServer(int port){
    //        this.port = port;
    //    }

    public LongLinkServerImpl() {
        //        this.port = NettyConfig.SERVER_PORT;
    }

    @Override
    public void bind() throws InterruptedException {
        ServerBootstrap b = null;
        if (isWindows()) {
            boss = new NioEventLoopGroup();
            work = new NioEventLoopGroup();
            b = new ServerBootstrap();
            b.group(boss, work)
                    .childHandler(longLinkChannelHandler)
                    .channel(NioServerSocketChannel.class)
                    // 保持连接数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
        } else {
            boss = new EpollEventLoopGroup();
            work = new EpollEventLoopGroup();
            b = new ServerBootstrap();
            b.group(boss, work)
                    .childHandler(longLinkChannelHandler)
                    .channel(EpollServerSocketChannel.class)
                    // 保持连接数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
        }
        ChannelFuture future = b.bind(port).sync();
        if (future.isSuccess()) {
            LOGGER.info("长连接服务端开启成功，port=" + this.port);
        } else {
            LOGGER.error("长连接服务端开启失败，port=" + this.port);
        }
        future.channel().closeFuture().sync();
    }

    @Override
    public void stop() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (work != null) {
            work.shutdownGracefully();
        }
        LOGGER.debug("colse 【NettyServer】 ok");
    }
}
