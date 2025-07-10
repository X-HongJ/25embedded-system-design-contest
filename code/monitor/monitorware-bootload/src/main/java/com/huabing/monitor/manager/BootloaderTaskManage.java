package com.huabing.monitor.manager;

import com.alibaba.fastjson.JSON;
import com.huabing.monitor.common.utils.TimeUtils;
import com.huabing.monitor.component.FileThreadPoolComponent;
import com.huabing.monitor.netty.dto.BootloadTask;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.BootloaderStatusEnum;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：kylinz
 * @description：管理来自网站的Bootloader任务
 * @date ：Created in 2020/11/13 11:06
 */
@Component
@EnableScheduling
public class BootloaderTaskManage {

    private final static Logger LOGGER = LoggerFactory.getLogger(BootloaderTaskManage.class);
    private final Map<Integer, BootloadTask> tasks = new ConcurrentHashMap<>();
    @Value("${bootloader.timeout}")
    private int timeout;
    @Value("${rabbitmq.queue.bootloader.status.exchange}")
    private String bootloaderStatusExchange;

    private String bootloaderStatusRouteKey;
    @Autowired
    FileThreadPoolComponent fileThreadPoolComponent;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public int getTaskCount(){
        return tasks.size();
    }

    public void addTask(Integer deviceId, BootloadTask task){
        tasks.put(deviceId,task);
    }

    public void deleteTask(Integer deviceId){
        tasks.remove(deviceId);
    }

    private boolean isTaskReady(Integer deviceId){
        if (!tasks.containsKey(deviceId))
            return false;
        return tasks.get(deviceId).isBoardReady() && tasks.get(deviceId).getRequest().isRequestReady();
    }

    public boolean hasTask(Integer deviceId){
        return tasks.containsKey(deviceId);
    }

    @Scheduled(fixedDelay = 60000)
    public void clearTimeoutTask(){
        LOGGER.info("清空超时任务");
        for (Map.Entry<Integer, BootloadTask> entry:tasks.entrySet()){
            BootloadTask value = entry.getValue();
            long timeLeft = timeout - (TimeUtils.getCurrentTimeStamp10()- value.getCreateTime());
            LOGGER.info("等待任务->deviceId:{}，剩余过期时间：{}s",Integer.toHexString(entry.getKey()),timeLeft);
            int k = entry.getKey();
            if ((TimeUtils.getCurrentTimeStamp10()-value.getCreateTime())>=timeout ||
                value.isComplete()){
                setBootloaderResponse(k,BootloaderStatusEnum.FAILED,"time out");
                sendBootloaderResponse(k);
                tasks.remove(k);
            }
        }
        LOGGER.info("等待任务数：{}",getTaskCount());
    }

    private BootloaderResponse getResponse(Integer deviceId){
        return tasks.get(deviceId).getResponse();
    }

    public void setBootloaderResponseStatus(Integer deviceId,BootloaderStatusEnum status){
        tasks.get(deviceId).getResponse().setStatus(status);
    }

    private void setBootloaderResponse(Integer deviceId, BootloaderStatusEnum status, String desc){
        BootloaderResponse response = tasks.get(deviceId).getResponse();
        response.setStatus(status);
        response.setDesc(desc);
    }

    public void setBoardReady(Integer deviceId){
        if (hasTask(deviceId)){
            tasks.get(deviceId).setBoardReady(true);
        }
    }

    public void setBootloaderRequest(int deviceId,BootloaderRequest request){
        tasks.get(deviceId).setRequest(request);
    }

    public void process(Integer deviceId){
        LOGGER.info(deviceId+" task ready:"+isTaskReady(deviceId));
        BootloadTask task = tasks.get(deviceId);
        // 超时任务不执行
        if (TimeUtils.getCurrentTimeStamp10()-task.getRequest().getTimestamp()>timeout){
            LOGGER.warn("task not execute---->timeout:"+task.getRequest());
            return;
        }
        if (isTaskReady(deviceId)) {
            fileThreadPoolComponent.addTask(task.getRequest(), task.getResponse());
        }
    }

    public void sendBootloaderResponse(Integer deviceId){
        BootloaderResponse response = getResponse(deviceId);
        rabbitTemplate.convertAndSend(bootloaderStatusExchange,
                bootloaderStatusRouteKey, JSON.toJSONString(response));
        LOGGER.info("send bootloader response to up stream:"+response);
        deleteTask(deviceId);
    }
}
