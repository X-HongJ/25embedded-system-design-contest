package com.huabing.monitor.netty.manage;

import com.huabing.monitor.common.utils.ByteUtils;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ping
 * @classname ClientManager
 * @description TODO
 * @date 2019/7/16 10:08
 */
@Component
@Scope("singleton")
public class ClientManager {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

    private Map<Integer, ClientInfo> clientMap =
            new ConcurrentHashMap<Integer, ClientInfo>();

    private Map<Integer, ClientInfo> getClientMap() {
        return clientMap;
    }

    public void addClient(int mechineId, ChannelHandlerContext ctx) {
        if (!getClientMap().containsKey(mechineId)){
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setCtx(ctx);
            clientInfo.setMechineId(mechineId);
            LOGGER.info("add client: {}", clientInfo);
            getClientMap().put(mechineId, clientInfo);
        }else {
            getClientMap().get(mechineId).setCtx(ctx);
        }
    }

    public void removeClient(Integer mechineId) {
        getClientMap().remove(mechineId);
        LOGGER.info("remove client mechineId={}", mechineId);
    }

    private ClientInfo isOnline(int mechineId) {
        Map<Integer, ClientInfo> clientManager = getClientMap();
        Set<Map.Entry<Integer, ClientInfo>> entries = clientMap.entrySet();
        for (Map.Entry<Integer, ClientInfo> client : entries) {
            Integer key = client.getKey();
            if (key.equals(mechineId)) {
                return client.getValue();
            }
        }
        return null;
    }

    public ChannelHandlerContext findClient(byte stationId, byte boardId) {
        int mechieId = ByteUtils.getShort(new byte[]{stationId, boardId});
        ClientInfo clientInfo = getClientMap().get(mechieId);
        if (clientInfo != null) {
            return clientInfo.getCtx();
        }
        return null;
    }

    public ChannelHandlerContext findClient(int mechieId) {
        listClient();
        ClientInfo clientInfo = getClientMap().get(mechieId);
        if (clientInfo != null) {
            return clientInfo.getCtx();
        }
        return null;
    }

    public List<Integer> listClient() {
        Map<Integer, ClientInfo> clientManager = getClientMap();
        Set<Map.Entry<Integer, ClientInfo>> entries = clientManager.entrySet();
        List<Integer> uuidList = new ArrayList<Integer>();
        for (Map.Entry<Integer, ClientInfo> client : entries) {
            Integer key = client.getKey();
            uuidList.add(key);
            LOGGER.debug("key=" + key + " value=" + client.getValue().getCtx());
        }
        return uuidList;
    }

    public int countClient() {
        return getClientMap().size();
    }


}
