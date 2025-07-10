package com.huabing.monitor.manager;

import com.alibaba.fastjson.JSONObject;
import com.huabing.monitor.common.utils.MathUtils;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import com.huabing.monitor.zookeeper.ZookeeperBase;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
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

    @Value("${curator.bigFile}")
    private String bootloaderBigFile;

    @Value("${curator.node_name}")
    private String nodeName;

    @Value("${curator.resource}")
    private String nodeResource;

    public void close(){
        zookeeperBase.close();
        LOGGER.info("zookeeper disconnected successfully");
    }

    public void registerSelf(){
        zookeeperBase.createNode(bootloaderTaskPath, CreateMode.PERSISTENT);
        zookeeperBase.createNode(bootloaderBigFile, CreateMode.PERSISTENT);

        LOGGER.info("create bootloader task node " + bootloaderTaskPath);
        LOGGER.info("create bootloader task node " + bootloaderBigFile);
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

    /**
     * 从ZooKeeper处获取任务消息
     * @param path
     * @return
     */
    public BootloaderRequest getTaskFromZookeeper(String path){
        if (path.equals(bootloaderTaskPath))
            return null;
        String data = zookeeperBase.getData(path);
        zookeeperBase.deleteNode(path);
        BootloaderRequest request = JSONObject.parseObject(data, BootloaderRequest.class);
        return request;
    }

    public List<String> getBigFileDeviceList() {
        List<String> res = zookeeperBase.getChildren(bootloaderBigFile);
        LOGGER.info("BigFileDeviceList is "+ Arrays.toString(res.toArray()));
        return res;
    }

    public List<String> getListTasks(){
        return zookeeperBase.getChildren(bootloaderTaskPath);
    }

    public void deleteExpiredNode(String root, Long ms){
        zookeeperBase.cleanTimeOutNodes(root, ms);
    }


}
