package com.atguigu.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component// 将该类注册为Spring容器的组件才能生效
@ConfigurationProperties(prefix = "order")// 指定配置文件中的前缀
@Data
public class OrderProperties {
    // 从配置文件中读取订单相关的配置
    String Timeout;
    String autoConfirm;
}
