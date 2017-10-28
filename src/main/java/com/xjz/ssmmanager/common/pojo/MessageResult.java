package com.xjz.ssmmanager.common.pojo;

import java.io.Serializable;

public class MessageResult<T> implements Serializable{
    private int statusCode;
    private TransData<T> data;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public TransData<T> getData() {
        return data;
    }

    public void setData(TransData<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageResult() {
    }

    public MessageResult(int statusCode, TransData<T> data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public MessageResult(int statusCode, TransData<T> data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }
}
