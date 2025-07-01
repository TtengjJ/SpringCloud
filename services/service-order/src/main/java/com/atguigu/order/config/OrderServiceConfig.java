package com.atguigu.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



@Configuration
public class OrderServiceConfig {

    // 配置Feign的重试机制
    @Bean
    Retryer retryer() {
        return new Retryer.Default(100, 1000, 5);

    }

    @Bean
    Logger.Level loggerLevel() {
        // 设置日志级别为FULL，FULL:可以打印请求和响应的头信息、正文和元数据
        return Logger.Level.FULL;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
