package com.huabing.monitor.listener;

import com.alibaba.fastjson.JSON;
import com.huabing.monitor.netty.dto.BootloadTask;
import com.huabing.monitor.manager.BootloaderTaskManage;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：kylinz
 * @description：监听bootloader任务队列
 * @date ：Created in 2020/11/12 22:20
 */
@Component
@RabbitListener(queues = "bootloader.task")
public class RabbitmqListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitmqListener.class);

    @Autowired
    private BootloaderTaskManage taskManager;

    @RabbitHandler
    public void addTaskToManager(String msg){
        BootloaderRequest request = JSON.parseObject(msg, BootloaderRequest.class);
        request.setRequestReady(true);
        Integer mecheineId = request.getDeviceId();
        if (!taskManager.hasTask(mecheineId)){
            //任务没有生成
            taskManager.addTask(mecheineId,new BootloadTask(mecheineId, request, false));
        }else {
            taskManager.setBootloaderRequest(mecheineId,request);
        }
        LOGGER.info("新增bootloader任务：{deviceId:{},request:{}}",mecheineId,request);
        taskManager.process(mecheineId);
    }
}
