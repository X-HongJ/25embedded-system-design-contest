package com.huabing.monitor.manager;

import com.alibaba.fastjson.JSONObject;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import com.huabing.monitor.zookeeper.ZookeeperBase;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：kylinz
 * @description：zookeeper业务交互
 * @date ：Created in 2020/11/24 23:02
 */
@Service
public class ZookeeperManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperManager.class);
    private final ZookeeperBase zookeeperBase;
    ZookeeperManager(ZookeeperBase zookeeperBase){
        this.zookeeperBase = zookeeperBase;
    }

    @Value("${curator.bootloader_task}")
    private String bootloaderTaskPath;

    @Value("${curator.node_name}")
    private String nodeName;

    @Value("${curator.resource}")
    private String nodeResource;

    public void close(){
        zookeeperBase.close();
        LOGGER.info("zookeeper disconnected successfully");
    }

    public void registerSelf(){
        if (!zookeeperBase.checkExist(bootloaderTaskPath)){
            zookeeperBase.createNode(bootloaderTaskPath, CreateMode.PERSISTENT);
            LOGGER.info("create bootloader task node " + bootloaderTaskPath);
        }
        if (!zookeeperBase.checkExist(nodeName)){
            String node = zookeeperBase.createNode(nodeName, CreateMode.PERSISTENT);
            zookeeperBase.setData(node, nodeResource);
        }
    }

    /**
     * task tree like bootloaderTaskPath/deviceId----->json like data
     * @param request
     */
    public void addBootloaderTask(BootloaderRequest request){
        int deviceId = request.getDeviceId();
        String path = bootloaderTaskPath + "/" + deviceId;
        String node = zookeeperBase.createNode(path, CreateMode.PERSISTENT);
        if (zookeeperBase.checkExist(node)){
            zookeeperBase.setData(node, JSONObject.toJSONString(request));
            LOGGER.info("create bootloader task to zookeeper success: "+request);
        }else {
            LOGGER.warn("create bootloader task failed: "+request);
        }
    }

    public BootloaderRequest getTaskFromZookeeper(String path){
        if (path.equals(bootloaderTaskPath))
            return null;
        String data = zookeeperBase.getData(path);
        LOGGER.info("获取到data"+data);
        zookeeperBase.deleteNode(path);
        BootloaderRequest request = JSONObject.parseObject(data, BootloaderRequest.class);
        return request;
    }

    public List<String> getListTasks(){
        return zookeeperBase.getChildren(bootloaderTaskPath);
    }

    public void deleteExpiredNode(String root, Long ms){
        zookeeperBase.cleanTimeOutNodes(root, ms);
    }


}
