package com.huabing.monitor.netty.handler;

import com.huabing.monitor.netty.codec.MyStringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author ping
 * @classname JsonChannelHandler
 * @description TODO
 * @date 2020/3/16 14:21
 */
public class JsonChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("decode", new MyStringDecoder());
        ch.pipeline().addLast("encode", new StringEncoder());
        ch.pipeline().addLast("jsonHandler", new JsonHandler());
    }
}
