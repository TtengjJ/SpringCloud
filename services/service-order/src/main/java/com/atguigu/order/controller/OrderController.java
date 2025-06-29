package com.atguigu.order.controller;


import com.atguigu.Order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope// 开启配置文件动态刷新功能
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    public OrderService orderService;

    // 从配置文件中读取订单相关的配置
//    @Value("${order.timeout}")
//    String orderTimeout;
//    @Value("${order.auto-confirm}")
//    String orderAutoConfirm;

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String config() {
        return "订单超时时间: " + orderProperties.getTimeout() + ", 自动确认收货: " + orderProperties.getAutoConfirm();
    }

    @GetMapping("create")
    public Order createOrder(@RequestParam Long userId, Long productId) {
        // 调用订单服务的创建订单方法
        return orderService.createOrder(userId, productId);
    }
}
