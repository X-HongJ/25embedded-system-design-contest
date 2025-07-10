package com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo;

import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;

/**
 * @author ping
 * @classname BaseMsg
 * @description 消息类的基类
 * @date 2019/7/17 10:07
 */
public class BaseMsg {

    private MsgType type;

    public BaseMsg() {
    }

    public BaseMsg(MsgType type) {
        this.type = type;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
}
