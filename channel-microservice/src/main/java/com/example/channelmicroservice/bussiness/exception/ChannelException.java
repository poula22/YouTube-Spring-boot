package com.example.channelmicroservice.bussiness.exception;

import lombok.Getter;

@Getter
public class ChannelException extends Exception {
    private final int code;
    public ChannelException(int code, String message) {
        super(message);
        this.code = code;
    }
}