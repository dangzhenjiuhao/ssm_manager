package com.xjz.ssmmanager.common.pojo;

import java.io.Serializable;

public class MessageResult<T> implements Serializable{
    private int statusCode;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MessageResult() {
    }

    public MessageResult(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
