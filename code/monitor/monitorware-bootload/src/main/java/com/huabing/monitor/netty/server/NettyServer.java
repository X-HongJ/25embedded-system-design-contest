package com.huabing.monitor.netty.server;

/**
 * @author ping
 * @classname NettyServer
 * @description 长连接处理类
 * @date 2019/9/17 10:11
 */
public interface NettyServer {

    public void bind() throws InterruptedException;

    public void stop();

}
