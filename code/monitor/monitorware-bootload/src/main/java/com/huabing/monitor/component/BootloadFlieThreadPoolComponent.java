//package com.huabing.monitor.com.huabing.monitor.component;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.List;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.RejectedExecutionHandler;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author ping
// * @classname OrderThreadPoolComponent
// * @description 发送反控的线程池
// * @date 2019/11/24 16:55
// */
//@Component
//public class BootloadFlieThreadPoolComponent implements BeanFactoryAware {
//
//    @Value("${async.executor.order.thread.core_pool_size}")
//    private int corePoolSize;
//    @Value("${async.executor.order.thread.max_pool_size}")
//    private int maxPoolSize;
//    @Value("${async.executor.order.thread.queue_capacity}")
//    private int queueCapacity;
//    @Value("${async.executor.order.thread.name.prefix}")
//    private String namePrefix;
//    @Value("${async.executor.order.thread.keep_alive_time}")
//    private int keepAliveTime;
//    private BeanFactory factory;
//
//    /**
//     * 创建线程池
//     */
//    private ThreadPoolExecutor threadPool;
//
//    @PostConstruct
//    public void threadPoolExecutor() {
//        threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), this.com.huabing.monitor.handler);
//    }
//
//    /**
//     * 丢弃旧数据
//     */
//    private final RejectedExecutionHandler com.huabing.monitor.handler = new ThreadPoolExecutor.DiscardOldestPolicy();
//
//
//    /**
//     * 将任务加入
//     */
//    public void addTask(String prePath, List<String> list) {
//        BootloadFliePathThread bootloadFliePathThread = factory.getBean(BootloadFliePathThread.class);
//        bootloadFliePathThread.setList(list);
//        bootloadFliePathThread.setPath(prePath);
//        threadPool.execute(bootloadFliePathThread);
//    }
//
//    /**
//     * 终止线程池+调度线程池
//     */
//    @PreDestroy
//    public void shutdown() {
//        threadPool.shutdown();
//    }
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        factory = beanFactory;
//    }
//
//}
