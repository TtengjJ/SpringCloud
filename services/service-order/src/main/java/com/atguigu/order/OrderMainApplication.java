package com.atguigu.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderMainApplication {
    public static void main(String[] args) {
        // 使用完整类名调用
        SpringApplication.run(OrderMainApplication.class, args);
    }
}