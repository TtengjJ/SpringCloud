package com.atguigu.order.feign.fallback;

import com.atguigu.Product.bean.Product;
import com.atguigu.order.feign.ProductFeignClient;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {

    @Override
    public Product getProductById(Long id) {
        System.out.println("兜底回调");
        Product product = new Product();
        product.setId(id);
        product.setProductName("商品信息不可用");
        product.setPrice(null);
        product.setNum(0);

        return product;
    }
}
