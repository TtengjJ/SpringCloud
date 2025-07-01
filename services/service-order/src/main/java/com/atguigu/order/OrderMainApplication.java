package com.atguigu.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableFeignClients// 开启Feign客户端功能
@EnableDiscoveryClient// 开启服务发现功能
@SpringBootApplication
public class OrderMainApplication {
    public static void main(String[] args) {
        // 使用完整类名调用
        SpringApplication.run(OrderMainApplication.class, args);
    }

    //项目启动监听配置文件变化
    //拿到变化值
    //发邮件

    //nacosConfigManager会自动注入
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
        return args -> {
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("service-order.properties",
                    "DEFAULT_GROUP",new Listener() {

                @Override
                public Executor getExecutor() {
                    return Executors.newFixedThreadPool(4);
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("配置文件发生变化，新的配置内容为: " + configInfo);
                    System.out.println("可以在这里添加发送邮件的逻辑");
                }
            });


            System.out.println("应用程序启动完成，配置文件已加载。");
            // 可以在这里添加发送邮件的逻辑
        };
    }
}