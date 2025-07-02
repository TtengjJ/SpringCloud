package com.atguigu.Order.bean;

import com.atguigu.Product.bean.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
public class Order {
    private Long id; // 订单ID
    private Long productId; // 商品ID
    private BigDecimal totalAmount; // 总金额
    private String userId; // 用户ID
    private String nickName; // 用户昵称
    private String address; // 收货地址
    private List<Product> productlist; // 订单中的商品列表

}
