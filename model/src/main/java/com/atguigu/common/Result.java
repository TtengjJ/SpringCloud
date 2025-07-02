package com.atguigu.common;

import lombok.Data;

@Data
public class Result {
    private Integer code; // 状态码
    private String message; // 响应消息
    private Object data; // 响应数据

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }

    // 返回成功状态
    public static Result ok() {
        return new Result(200, "success", null);
    }
    // 返回成功状态，并携带数据
    public static Result ok(Object data) {
        return new Result(200, "success", data);
    }
    // 返回错误状态
    public static Result error() {
        return new Result(500, "error", null);
    }
    // 返回错误状态，并携带状态码和错误消息
    public static Result error(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
