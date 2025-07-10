package com.huabing.monitor.netty.handler;

import com.huabing.monitor.netty.codec.ByteToMsgDecoder;
import com.huabing.monitor.netty.codec.MyStringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author ping
 * @classname LongLinkChannelHandler
 * @description TODO
 * @date 2019/7/3 10:24
 */
@Component
public class LongLinkChannelHandler extends ChannelInitializer<SocketChannel> {

    private final static Logger LOGGER = LoggerFactory.getLogger(LongLinkChannelHandler.class);


    @Autowired
    private LongLinkHandler longLinkHandler;

    @Value("${netty.server.heart.readTimeout}")
    private int readTimeout;
    @Value("${netty.server.heart.writeTimeout}")
    private int writeTimeout;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // readout之后无数据上传断开连接
        LOGGER.info("readTimeout："+readTimeout);
        LOGGER.info("writeTimeout："+writeTimeout);
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(readTimeout, writeTimeout, 0));
        ch.pipeline().addLast("idleStateTrigger", new ServerIdleStateTrigger());
        ch.pipeline().addLast("byteToMsgDecoder", new ByteToMsgDecoder());
        ch.pipeline().addLast("encode", new StringEncoder());
        ch.pipeline().addLast("longLinkHandler", longLinkHandler);
    }
}
