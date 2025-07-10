package com.huabing.monitor.zookeeper;

import com.huabing.monitor.manager.ZookeeperManager;
import com.huabing.monitor.netty.dto.BootloadTask;
import com.huabing.monitor.manager.BootloaderTaskManage;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ：kylinz
 * @description：任务监听器
 * @date ：Created in 2020/12/10 23:48
 */
@Component
public class BootloaderTaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootloaderTaskListener.class);

    private final CuratorFramework client;

    @Value("${curator.bootloader_task}")
    private String bootloaderTaskPath;
    @Autowired
    private ZookeeperManager zookeeperManager;

    @Autowired
    private BootloaderTaskManage taskManager;

    private  TreeCache treeCache;

    public TreeCache getTreeCache() {
        return treeCache;
    }

    public void setTreeCache(TreeCache treeCache) {
        this.treeCache = treeCache;
    }

    public BootloaderTaskListener(CuratorFramework client) {
        this.client = client;
    }

    /**
     * 对于启动前已存在的任务执行或超时处理
     */
    public void preFetch(){
        List<String> listTasks = zookeeperManager.getListTasks();
        if (listTasks.isEmpty())
            return;
        for (String path:listTasks){
            LOGGER.info("pre fetched tasks:"+path);
            BootloaderRequest task = zookeeperManager.getTaskFromZookeeper(bootloaderTaskPath+"/"+path);
            LOGGER.info("--------由于pre创建的任务"+task.toString());
            handleBootloaderRequest(task);
        }
    }

    private void handleBootloaderRequest(BootloaderRequest request){
        if (request!=null) {
            request.setRequestReady(true);
            Integer mecheineId = request.getDeviceId();
            if (!taskManager.hasTask(mecheineId)) {
                //任务没有生成
                taskManager.addTask(mecheineId, new BootloadTask(mecheineId, request, false));
            } else {
                taskManager.setBootloaderRequest(mecheineId, request);
            }
            LOGGER.info("-----新增bootloader任务：{deviceId:{},request:{}}", mecheineId, request);
            taskManager.process(mecheineId);
        }
    }

    public void startListen() throws Exception {
        treeCache = new TreeCache(client, bootloaderTaskPath);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                TreeCacheEvent.Type eventType = treeCacheEvent.getType();
                switch (eventType){
//                    case NODE_ADDED:
//                        BootloaderRequest request = zookeeperManager.getTaskFromZookeeper(treeCacheEvent.getData().getPath());
//                        LOGGER.info("request------------------"+request.toString());
//                        handleBootloaderRequest(request);
//                        break;
                    case NODE_UPDATED:
                        BootloaderRequest request = zookeeperManager.getTaskFromZookeeper(treeCacheEvent.getData().getPath());
                        LOGGER.info("request------------------"+request.toString());
                        handleBootloaderRequest(request);
                        break;
                    case NODE_REMOVED:
                        break;
                }
            }
        });

        treeCache.start();
        LOGGER.info("task listener start to listen....");

    }
    @Scheduled(fixedDelay = 10000)
    public synchronized void  ListenByScheduled(){
        LOGGER.info("定时获取任务");
        List<String> listTasks = zookeeperManager.getListTasks();
        if (listTasks.isEmpty())
            return;
        for (String path:listTasks){
            LOGGER.info("定时任务: "+path);
            BootloaderRequest task = zookeeperManager.getTaskFromZookeeper(bootloaderTaskPath+"/"+path);
            LOGGER.info("-----由于定时任务创建的任务"+task.toString());
            handleBootloaderRequest(task);
        }

    }

}
