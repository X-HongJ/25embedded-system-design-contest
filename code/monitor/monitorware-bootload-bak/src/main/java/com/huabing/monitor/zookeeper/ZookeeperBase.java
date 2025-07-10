package com.huabing.monitor.zookeeper;

import com.huabing.monitor.common.utils.TimeUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：kylinz
 * @description：封装zk方法
 * @date ：Created in 2020/11/24 22:19
 */
@Component
public class ZookeeperBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperBase.class);
    @Autowired
    private CuratorFramework client;

    public boolean checkExist(String node){
        boolean isExist = false;
        try {
            isExist = client.checkExists().forPath(node) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public String createNode(String node, CreateMode mode){
        try {
            //if (!checkExist(node)) {
            String path = client.create().creatingParentsIfNeeded().withMode(mode).forPath(node);
            LOGGER.info("create node:" + node);
            return path;
            //}
        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error("create node {} error",node);
            return null;
        }
    }

    public void deleteNode(String node){
        try {
            //if (checkExist(node)) {
                client.delete().deletingChildrenIfNeeded().forPath(node);
                LOGGER.info("delete node:" + node);
            //}
        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error("delete node {} error",node);
        }
    }

    public List<String> getChildren(String node){
        List<String> strings = null;
        try {
            //if (checkExist(node)) {
                strings = client.getChildren().forPath(node);
            //}
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return strings;
    }

    public void setData(String node, String data){
        try {
            client.setData().forPath(node,data.getBytes());
            LOGGER.info("update node:{}---->{}",node,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData(String node){
        String data = null;
        try {
            data = new String(client.getData().forPath(node), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void close(){
        client.close();
    }

    /**
     * 考虑使用递归实现，目前默认两级深度
     * @param root
     * @param timeOutMs
     */
    public void cleanTimeOutNodes(String root, Long timeOutMs){
        Stat stat;
        List<String> oneLevel = null;
        List<String> twoLevel = null;
        Set<String> willDeleteNode = new HashSet<>();
        try {
            stat = client.checkExists().forPath(root);
            if (stat != null){
                // 获得一级目录
                if (stat.getNumChildren()>0){
                    oneLevel = this.getChildren(root);
                }
            }
            if (oneLevel != null){
                for (String dir:oneLevel){
                    String path = root + "/" +dir;
                    stat = client.checkExists().forPath(path);
                    if (stat != null){
                        if (stat.getNumChildren()>0){
                            twoLevel = dirJoin(path,this.getChildren(path));
                        }else {
                            willDeleteNode.add(path);
                        }
                    }
                }
            }

            if (twoLevel != null) {
                for (String dir : twoLevel){
                    stat = client.checkExists().forPath(dir);
                    if (TimeUtils.getCurrentTimeStamp13() - stat.getMtime() > timeOutMs){
                        willDeleteNode.add(dir);
                    }
                }
            }


            if (!willDeleteNode.isEmpty()){
                for (String delete:willDeleteNode){
                    client.delete().deletingChildrenIfNeeded().forPath(delete);
                }
            }

            LOGGER.info("delete expired node:"+willDeleteNode);
        } catch (Exception e) {
            LOGGER.error("delete expired node failed"+e);
        }
    }

    private List<String> dirJoin(String baseDir,List<String> dirs){
        List<String> joined = new ArrayList<>();
        for (String dir:dirs){
            joined.add(baseDir+"/"+dir);
        }
        return joined;
    }
}
