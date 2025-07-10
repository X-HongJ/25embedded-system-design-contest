package com.huabing.monitor.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ping
 * @classname ServerIdleStateTrigger
 * @description 具体实现心跳机制
 * @date 2019/7/3 10:38
 */
public class ServerIdleStateTrigger extends ChannelInboundHandlerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerIdleStateTrigger.class);

//    int readIdleTimes = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            String eventType = null;
            switch (state) {
                case READER_IDLE:
//                    eventType = "读空闲";
                    LOGGER.info(ctx.channel().remoteAddress() + " read idle ");
                    ctx.channel().disconnect();
//                    readIdleTimes++;
                    break;
                case WRITER_IDLE:
//                    eventType = "写空闲";
                    // 不处理
                    break;
                case ALL_IDLE:
//                    eventType = "读写空闲";
                    // 不处理
                    break;
                default:
                    break;
            }
//            if (state == IdleState.READER_IDLE) {
//                // 在规定时间内没有收到客户端的上行数据, 主动断开连接
//                ctx.disconnect();
//            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
