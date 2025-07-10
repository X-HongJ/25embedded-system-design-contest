package com.huabing.monitor.netty.manage;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentMap;

/**
 * @author ping
 * @classname ClientInfo
 * @description TODO
 * @date 2019/7/12 9:57
 */
public class ClientInfo {
    private Integer mechineId;
    private ChannelHandlerContext ctx;

    @Override
    public String toString() {
        return "ClientInfo{" +
                "mechineId=" + mechineId +
                ", ctx=" + ctx +
                '}';
    }

    public Integer getMechineId() {
        return mechineId;
    }

    public void setMechineId(Integer mechineId) {
        this.mechineId = mechineId;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
