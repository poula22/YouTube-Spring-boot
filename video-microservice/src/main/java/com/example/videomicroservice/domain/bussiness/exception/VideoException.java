package com.example.videomicroservice.domain.bussiness.exception;

import lombok.Getter;

@Getter
public class VideoException extends Exception{
    private final int code;
    public VideoException(int code, String message) {
        super(message);
        this.code = code;
    }
}
