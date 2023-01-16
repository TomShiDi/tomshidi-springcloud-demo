package com.tomshidi.demo.dto;

/**
 * @author tangshili
 * @date 2023/1/13 16:29
 */
public class CommonResponse<T> {
    private String status;

    private String message;

    private T data;

    public CommonResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
