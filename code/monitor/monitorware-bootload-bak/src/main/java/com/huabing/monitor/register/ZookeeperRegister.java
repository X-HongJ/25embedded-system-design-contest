//package com.huabing.monitor.register;
//
//import com.huabing.monitor.com.huabing.monitor.manager.ZookeeperManager;
//import com.huabing.monitor.zookeeper.ServiceRegister;
//import com.huabing.monitor.zookeeper.impl.WatchBootloadImpl;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.type.AnnotationMetadata;
//
///**
// * @author ping
// * @classname ZookeeperRegister
// * @description 将zookeeper的组件注入
// * @date 2019/12/27 21:26
// */
//public class ZookeeperRegister implements ImportBeanDefinitionRegistrar {
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
//        registerZookeeperIfRequire(beanDefinitionRegistry);
//    }
//
//
//    private void registerZookeeperIfRequire(BeanDefinitionRegistry beanDefinitionRegistry){
//        BeanDefinition watchBootloadImplBeanDefinition = BeanDefinitionBuilder
//                .rootBeanDefinition(WatchBootloadImpl.class).getBeanDefinition();
//        BeanDefinition zookeeperServiceBeanDefinition = BeanDefinitionBuilder
//                .rootBeanDefinition(ServiceRegister.class).getBeanDefinition();
//        BeanDefinition zookeeperManageBeanDefinition = BeanDefinitionBuilder
//                .rootBeanDefinition(ZookeeperManager.class).getBeanDefinition();
//        beanDefinitionRegistry.registerBeanDefinition("watchBootloadImpl", watchBootloadImplBeanDefinition);
//        beanDefinitionRegistry.registerBeanDefinition("zookeeperService", zookeeperServiceBeanDefinition);
//        beanDefinitionRegistry.registerBeanDefinition("zookeeperManage", zookeeperManageBeanDefinition);
//    }
//}
