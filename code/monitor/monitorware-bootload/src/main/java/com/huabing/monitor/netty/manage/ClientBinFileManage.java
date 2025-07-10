package com.huabing.monitor.netty.manage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ping
 * @classname ClientBinFileManage
 * @description 存放客户端的需要发送的bin文件
 * @date 2019/11/24 17:56
 */
@Component
@Scope("singleton")
public class ClientBinFileManage {

    private final Map<Integer,String> clientBinFileMap = new ConcurrentHashMap<Integer, String>();

    public Map<Integer, String> getClientBinFileMap() {
        return clientBinFileMap;
    }

    public void addBinFile(Integer mechineId, String filePath){
        getClientBinFileMap().put(mechineId, filePath);
    }

    public String removeBinFile(Integer mechineId){
        return getClientBinFileMap().remove(mechineId);
    }

}
