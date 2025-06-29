package com.atguigu.Order.bean;

import com.atguigu.Product.bean.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
public class Order {
    private Long id;
    private Long productId;
    private BigDecimal totalAmount;
    private String userId;
    private String nickName;
    private String address; // 收货地址
    private List<Product> productlist; // 订单中的商品列表

}
