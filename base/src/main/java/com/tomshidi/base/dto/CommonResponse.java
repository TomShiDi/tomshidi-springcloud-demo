package com.tomshidi.base.dto;

/**
 * @author tomshidi
 * @date 2023/1/13 16:29
 */
public class CommonResponse<T> {
    private Integer code;

    private String message;

    private T data;

    public CommonResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static CommonResponse<Object> SUCCESS() {
        return new CommonResponse<>(0, "执行成功", null);
    }
}
