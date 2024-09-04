package com.example.usermicroservice.domain.bussiness.exception;

public class CustomAuthException extends Exception {
    public CustomAuthException(String message) {
        super(message);
    }
}
