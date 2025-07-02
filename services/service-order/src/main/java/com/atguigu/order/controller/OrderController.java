package com.atguigu.order.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.Order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;


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

    @GetMapping("/create")
    public Order createOrder(@RequestParam Long userId, Long productId) {
        // 调用订单服务的创建订单方法
        return orderService.createOrder(userId, productId);
    }

    @GetMapping("/seckill")
    @SentinelResource(value="seckill-order",fallback = "seckillFallback")
    public Order seckill(@RequestParam Long userId, Long productId) {
        // 调用订单服务的创建订单方法
       Order order = orderService.createOrder(userId, productId);
       order.setId(Long.MAX_VALUE); // 设置订单ID为最小值，模拟秒杀订单
        // 模拟秒杀逻辑
        System.out.println("秒杀成功，订单ID: " + order.getId());
        return order;
    }

    //声明一个降级方法,Throwable比BlockException更通用
    public Order seckillFallback(Long userId, Long productId, Throwable exception) {
        System.out.println(" 秒杀失败，订单ID: " + productId);
        // 调用订单服务的创建订单方法
        Order order = new Order();
        order.setId(0L);
        order.setUserId(String.valueOf(userId));
        order.setAddress("异常信息："+exception.getClass());
        return order;
    }
}
