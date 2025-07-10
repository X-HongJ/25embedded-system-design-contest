package com.huabing.monitor.component;

import com.huabing.monitor.netty.manage.ClientInfo;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ping
 * @classname FileThreadPoolComponent
 * @description 发送文件的线程池
 * @date 2019/11/24 17:30
 */
@Component
public class FileThreadPoolComponent implements BeanFactoryAware {

    @Value("${async.executor.file.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${async.executor.file.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.executor.file.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${async.executor.file.thread.name.prefix}")
    private String namePrefix;
    @Value("${async.executor.file.thread.keep_alive_time}")
    private int keepAliveTime;
    private BeanFactory factory;

    /**
     * 创建线程池
     */
    private ThreadPoolExecutor threadPool;

    @PostConstruct
    public void threadPoolExecutor() {
        threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), this.handler);
    }

    /**
     * 丢弃旧数据
     */
    private final RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();


    /**
     * 将任务加入
     */
    public void addTask(Integer mechieId, String file) {
        FileThread fileThread = factory.getBean(FileThread.class);
        fileThread.setPath(file);
        fileThread.setMechieId(mechieId);
        threadPool.execute(fileThread);
    }

    public void addTask(BootloaderRequest request, BootloaderResponse response){
        FileThread fileThread = factory.getBean(FileThread.class);
        fileThread.setPath(request.getFilePath());
        fileThread.setMechieId(request.getDeviceId());
        fileThread.setResponse(response);
        threadPool.execute(fileThread);
    }

    /**
     * 终止订单线程池+调度线程池
     */
    @PreDestroy
    public void shutdown() {
        threadPool.shutdown();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }

}
