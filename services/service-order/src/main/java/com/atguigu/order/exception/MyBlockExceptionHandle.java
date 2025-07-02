package com.atguigu.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.io.PrintWriter;

//重写Sentinel的BlockExceptionHandler接口，用于处理限流或降级异常
//重启服务后，Sentinel会自动加载这个类，Sentinel控流服务也会失效
@Component
public class MyBlockExceptionHandle implements BlockExceptionHandler {


    // 注入ObjectMapper对象可以将Result对象转换为JSON字符串
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private View error;

    // 实现BlockExceptionHandler接口的handle方法
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String resourceName, BlockException e) throws Exception {
        // 设置响应内容类型,要放到获取PrintWriter之前
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        // 获取PrintWriter对象，用于向响应中写入数据
        PrintWriter writer = httpServletResponse.getWriter();


        // 创建Result对象，设置错误信息
        Result error = Result.error(500,resourceName + "服务不可用，请稍后再试！" + e.getClass());

        // 将Result对象转换为JSON字符串
        String json = objectMapper.writeValueAsString(error);
        // 将JSON字符串写入响应
        writer.write(json);

        writer.flush();
        writer.close();
    }
}
