/*
package com.gi.xm.platform.conf.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * Created by vincent on 16-8-25.
 *//*

@Configuration
public class DubboConfig {


    @Value("${dubbo.application.name}")
    public static final String APPLICATION_NAME = "xm-service-rest";


    @Value("${dubbo.registry.address}")
    public static final String REGISTRY_ADDRESS = "zookeeper://10.9.21.172:2181";


    @Value("${dubbo.scan.basebackage}")
    public static final String ANNOTATION_PACKAGE = "com.gi.xm.platform";

    @Value("${dubbo.protocol.dubbo.port}")
    public static final Integer PORT = 20881;


    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(APPLICATION_NAME);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(REGISTRY_ADDRESS);
        return registryConfig;
    }

    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(ANNOTATION_PACKAGE);
        return annotationBean;
    }
}
*/