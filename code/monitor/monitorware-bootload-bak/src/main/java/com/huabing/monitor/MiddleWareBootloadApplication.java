package com.huabing.monitor;

import com.huabing.monitor.netty.manage.NettyServerManager;
import com.huabing.monitor.manager.ZookeeperManager;
import com.huabing.monitor.zookeeper.BootloaderTaskListener;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author ping
 * @classname MiddleWareApplication
 * @description TODO
 * @date 2019/7/1 16:01
 */
@SpringBootApplication
public class MiddleWareBootloadApplication {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(MiddleWareBootloadApplication.class, args);
        final NettyServerManager nettyServerBoostrap = context.getBean(NettyServerManager.class);
        final BootloaderTaskListener taskListener = context.getBean(BootloaderTaskListener.class);
        // 开启netty服务
        nettyServerBoostrap.startServer();
        ZookeeperManager zookeeperManage=null;
        try {
            zookeeperManage = context.getBean(ZookeeperManager.class);

        } catch (NoSuchBeanDefinitionException e) {
            System.out.println(e.getMessage());
        }
        if (zookeeperManage!=null){
            // 开启zookeeper服务
            zookeeperManage.registerSelf();
        }
        // 处理监听前的任务
        taskListener.preFetch();
        //开始监听bootloader节点
        taskListener.startListen();
        final ZookeeperManager finalZookeeperManage = zookeeperManage;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                nettyServerBoostrap.stopServer();
                if (finalZookeeperManage!=null){
                    // 关闭zookeeper服务
                    finalZookeeperManage.close();
                }
                System.out.println("关闭成功");
            }
        });
    }


}
