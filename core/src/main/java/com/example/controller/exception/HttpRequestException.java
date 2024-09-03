package com.example.controller.exception;

import lombok.Getter;
@Getter
public class HttpRequestException extends Exception{
    private final int code;
    public HttpRequestException(String message, int code) {
        super(message);
        this.code = code;
    }
}