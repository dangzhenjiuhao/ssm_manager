package com.xjz.ssmmanager.common.execeptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String message) {
        super(message);
    }
}
