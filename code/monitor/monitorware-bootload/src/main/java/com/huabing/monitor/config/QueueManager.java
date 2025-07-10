package com.huabing.monitor.config;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author KingsLu
 * @classname QueueManager
 * @description
 * 用于管理rabbitMq的Queue 可配置
 * @date 2021/7/27 19:26
 */
@Component
public class QueueManager {
    @Resource
    private RabbitQueue bootloaderStatusQueue;

    public RabbitQueue getBootloaderStatusQueue() {
        return bootloaderStatusQueue;
    }

}
