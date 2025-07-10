package com.huabing.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {


    /**
     * 关于bootloader状态队列相关的参数
     */
    @Bean
    public RabbitQueue bootloaderStatusQueue(@Value("${rabbitmq.queue.bootloader.status.exchange}")String exchange, @Value("${rabbitmq.queue.bootloader.status.name}")String name, @Value("${rabbitmq.queue.bootloader.status.routeKey}")String routeKey) {
        return new RabbitQueue(exchange, name, routeKey);
    }



}
