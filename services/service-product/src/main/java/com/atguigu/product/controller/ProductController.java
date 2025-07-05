package com.atguigu.product.controller;


import com.atguigu.Product.bean.Product;
import com.atguigu.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //查询商品信息
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long productId, HttpServletRequest request) {
        request.getHeader("X-Token"); // 读取请求头中的X-Token信息
        System.out.println("请求头中的X-Token: " + request.getHeader("X-Token"));
        return productService.getProduct(productId);
    }
}
