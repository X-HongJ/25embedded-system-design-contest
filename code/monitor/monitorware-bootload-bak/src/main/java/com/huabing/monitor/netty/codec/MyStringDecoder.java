package com.huabing.monitor.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author ping
 * @classname MyStringDecoder
 * @description TODO
 * @date 2019/9/24 21:10
 */
public class MyStringDecoder extends StringDecoder {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyStringDecoder.class);

    public MyStringDecoder() {
        super();
    }

    public MyStringDecoder(Charset charset) {
        super(charset);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        super.decode(ctx, msg, out);
    }
}
