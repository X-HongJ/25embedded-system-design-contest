package com.huabing.monitor.netty.manage;

import com.huabing.monitor.netty.server.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author ping
 * @classname NettyServerManager
 * @description netty服务的管理器， 此服务有一个接受终端板的netty服务端 以及一个向zk服务注册并定时发送心跳的一个客户端
 * @date 2019/7/16 11:37
 */
@Service
@Scope("singleton")
public class NettyServerManager {

    private Logger logger = LoggerFactory.getLogger(NettyServerManager.class);

    private final NettyServer longLinkServer;
    @Autowired
    private NettyServerManager(@Qualifier("LongLinkServerImpl") NettyServer longLinkServer) {
        this.longLinkServer = longLinkServer;
    }

    @Autowired(required = false)  // 表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错
    @Qualifier("JsonServerImpl")  // 表明了哪个实现类才是我们所需要的
    private NettyServer jsonServer;

    public NettyServer getLongLinkServer() {
        return longLinkServer;
    }



    public void startServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    getLongLinkServer().bind();
                } catch (Exception e) {
                    logger.info("failed to start longlink server !" + e);
                }
            }
        }.start();
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                if (jsonServer!=null){
        //                    try {
        //                        jsonServer.bind();
        //                    } catch (InterruptedException e) {
        //                        logger.info("failed to start json server !");
        //                    }
        //                }
        //            }
        //        }).start();
    }

    public void stopServer() {
        getLongLinkServer().stop();
        if (jsonServer != null) {
            jsonServer.stop();
        }
        logger.info("Netty server stopped successfully");
    }
}
