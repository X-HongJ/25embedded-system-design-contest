package com.huabing.monitor.config;

import com.huabing.monitor.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kylinz
 * @description rabbitmq配置文件
 * 工作队列模式 一对一
 * @date 2020/05/29 19:33
 */
@Configuration
public class RabbitmqConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitmqConfig.class);
    // bootloader给网站的状态回复
    private static final String BOOTLOADER_STATUS_QUEUE = "bootloader.status";
    // bootloader任务发布、监听队列
    private static final String  BOOTLOADER_TASK_QUEUE = "bootloader.task";
    // bootloader相关的交换机
    private static final String EXCHANGE = "bootloaderExchange";

    @Bean(BOOTLOADER_STATUS_QUEUE)
    public Queue bootloaderStatusQueueDeclare(){
        LOGGER.info("bootloader状态队列："+BOOTLOADER_STATUS_QUEUE);
        return new Queue(BOOTLOADER_STATUS_QUEUE);
    }

    @Bean(BOOTLOADER_TASK_QUEUE)
    public Queue bootloaderTaskQueueDeclare(){
        LOGGER.info("bootloader任务队列："+BOOTLOADER_TASK_QUEUE);
        return new Queue(BOOTLOADER_TASK_QUEUE);
    }

    @Bean(EXCHANGE)
    public DirectExchange bootloaderExchangeDeclare(){
        LOGGER.info("声明bootloader交换机：{}",EXCHANGE);
        return (DirectExchange)ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding bootloaderStatusQueueBinding(@Qualifier(BOOTLOADER_STATUS_QUEUE)Queue q,
                                                @Qualifier(EXCHANGE)Exchange exchange){
        LOGGER.info("绑定状态队列至交换机");
        return BindingBuilder.bind(q).to(exchange).with(QueueEnum.BOOTLOADER_STATUS_QUEUE.routeKey).noargs();
    }

    @Bean
    public Binding bootloaderTaskQueueBinding(@Qualifier(BOOTLOADER_TASK_QUEUE)Queue q,
                                                @Qualifier(EXCHANGE)Exchange exchange){
        LOGGER.info("绑定任务队列至交换机");
        return BindingBuilder.bind(q).to(exchange).with(QueueEnum.BOOTLOADER_TASK_QUEUE.routeKey).noargs();
    }

}
