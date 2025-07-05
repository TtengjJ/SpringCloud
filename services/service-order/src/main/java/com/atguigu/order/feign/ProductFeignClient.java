package com.atguigu.order.feign;

import com.atguigu.Product.bean.Product;
import com.atguigu.order.feign.fallback.ProductFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-product", path = "/product",fallback = ProductFeignClientFallback.class)// 远程调用，指定服务名和路径
public interface ProductFeignClient {

    //mvc注解两套使用逻辑
    //Controller上，接收请求
    //FeignClient上，远程调用,发送请求
    @GetMapping("/api/product/product/{id}") // 使用GET请求，路径为/product/{productId}
    Product getProductById(@PathVariable Long id); // 定义远程调用的方法，参数为商品ID
}
