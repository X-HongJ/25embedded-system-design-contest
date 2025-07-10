package com.huabing.monitor.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：kylinz
 * @description：配置zookeeper客户端
 * @date ：Created in 2020/11/24 21:34
 */
@Configuration
public class CuratorConfiguration {

    @Value("${curator.retryCount}")
    private int retryCount;

    @Value("${curator.elapsedTimeMs}")
    private int elapsedTimeMs;

    @Value("${curator.connectString}")
    private String connectString;

    @Value("${curator.sessionTimeoutMs}")
    private int sessionTimeoutMs;

    @Value("${curator.connectionTimeoutMs}")
    private int connectionTimeoutMs;

    @Value("${curator.auth}")
    private String auth;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .authorization("digest",auth.getBytes())
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(new RetryNTimes(retryCount, elapsedTimeMs)).build();
    }
}
