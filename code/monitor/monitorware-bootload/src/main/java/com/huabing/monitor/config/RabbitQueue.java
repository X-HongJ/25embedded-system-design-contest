package com.huabing.monitor.config;

public class RabbitQueue {
    /**
     * 交换名称
     */
    public String exchange;
    /**
     * 队列名称
     */
    public String name;
    /**
     * 路由键
     */
    public String routeKey;

    public RabbitQueue(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}