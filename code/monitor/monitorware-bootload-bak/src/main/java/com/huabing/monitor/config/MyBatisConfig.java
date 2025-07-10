package com.huabing.monitor.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ping
 * @classname MyBatisConfig
 * @description mybatis配置
 * @date 2019/11/27 14:04
 */
@Configuration
@MapperScan(basePackages={"com.huabing.monitor.mbg.mapper","com.huabing.monitor.mapper"})
public class MyBatisConfig {
}
