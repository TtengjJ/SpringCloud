package com.atguigu.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

//向商品服务的Feign请求中添加一个名为"X-Token"的自定义请求头
@Component
//请求拦截器，用于在Feign请求中添加自定义的请求头
public class XTokenRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 在请求头中添加一个名为"X-Token"的自定义头部，值为一个随机生成的UUID
        System.out.println("X-TokenRequestInterceptor apply");
        template.header("X-Token", UUID.randomUUID().toString());
    }
}
