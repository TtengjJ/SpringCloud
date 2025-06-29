package com.atguigu.order.service;


import com.atguigu.Order.bean.Order;

public interface OrderService {
    Order createOrder(Long userId, Long productId);
}
