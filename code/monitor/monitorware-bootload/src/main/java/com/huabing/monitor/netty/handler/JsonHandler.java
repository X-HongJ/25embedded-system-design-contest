package com.huabing.monitor.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.netty.manage.ClientInfo;
import com.huabing.monitor.netty.manage.ClientManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @author ping
 * @classname LongLinkHandler
 * @description TODO
 * @date 2019/7/3 10:14
 */
@Component
@ChannelHandler.Sharable
@Scope("singleton")
public class JsonHandler extends SimpleChannelInboundHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);

    @Autowired private ClientManager clientManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("msg:" + msg);
        if (Objects.isNull(msg)) {
            return;
        } else if ('{' == msg.toString().charAt(0)) {
            JSONObject jsonObject = JSON.parseObject(msg.toString());
            if ("update".equals(jsonObject.getString("action"))
                    && "bootload".equals(jsonObject.getString("type"))) {
                // 下发更新指令，让板子跳转bootload
                short mechId =
                        ByteUtils.getShort(
                                new byte[] {
                                    jsonObject.getByte("building"), jsonObject.getByte("room_id")
                                });
                LOGGER.info("mechId" + mechId);
                ChannelHandlerContext clientCtx = clientManager.findClient(mechId);
                if (clientCtx != null) {
                    clientCtx.channel().writeAndFlush("BD2");
                }
            }
        }
    }
}
