package com.atguigu.order.service.impl;


import com.atguigu.Order.bean.Order;
import com.atguigu.Product.bean.Product;
import com.atguigu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OrderServiceimpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Order createOrder(Long userId, Long productId) {
        Product product = getProductFroRemote(productId);

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
