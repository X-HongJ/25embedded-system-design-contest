package com.huabing.monitor.dto;

import com.huabing.monitor.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ping
 * @classname QueueEnum
 * @description
 * 消息队列的参数
 * 一个交换机多个队列
 * @date 2019/10/28 19:26
 */
public enum QueueEnum {

    // bootloader给网站的状态回复
    BOOTLOADER_STATUS_QUEUE("bootloaderExchange","bootloader.status","bootloader.status.key"),
    // bootloader任务发布、监听队列
    BOOTLOADER_TASK_QUEUE("bootloaderExchange","bootloader.task","bootloader.task.key"),;
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

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

}
