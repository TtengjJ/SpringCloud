package com.atguigu.product.service.impl;


import com.atguigu.Product.bean.Product;
import com.atguigu.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceimpl implements ProductService {
    @Override
    public Product getProduct(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("100"));
        product.setProductName("华为手机"+productId);
        product.setNum(2);

        return product;
    }
}
