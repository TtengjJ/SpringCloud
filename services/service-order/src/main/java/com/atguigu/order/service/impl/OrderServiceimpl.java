package com.atguigu.order.service.impl;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.Order.bean.Order;
import com.atguigu.Product.bean.Product;
import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OrderServiceimpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ProductFeignClient productFeignClient;

    @SentinelResource(value = "createOrder", blockHandler = "createOrderBlockHandler")// Sentinel指定资源名称和限流处理方法
    @Override
    public Order createOrder(Long userId, Long productId) {
//        Product product = getProductFroRemote(productId);

        // 使用FeignClient远程调用商品服务获取商品信息
        Product product= productFeignClient.getProductById(productId);

        Order order = new Order();
        order.setId(1L);
        order.setProductId(productId);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId.toString());
        order.setNickName("用户" + userId);
        order.setAddress("北京市朝阳区某小区");
        order.setProductlist(List.of(product)); // 订单列表
        return order;
    }

    // 限流处理方法,blockHandler指定的方法返回兜底数据
    public Order createOrderBlockHandler(Long userId, Long productId, BlockException e) {
        log.error("限流了，无法创建订单: {}", e.getMessage());
        Order order = new Order();
        order.setId(0L);
        order.setUserId(userId.toString());
        order.setAddress("异常信息："+e.getClass());
        return order;
    }


    private Product getProductFroRemote(Long productId) {
        // 1、获取商品所在所有机器的ip+port
        List<ServiceInstance> instances= discoveryClient.getInstances("service-product");

        ServiceInstance instance = instances.getFirst();
        //远程url
        String url= "http://"+ instance.getHost()+":"+instance.getPort()+ "/product/" + productId;
        log.info("远程请求，url:{}", url);

        // 2、发送http请求，获取商品信息
        return restTemplate.getForObject(url, Product.class);
    }
}
