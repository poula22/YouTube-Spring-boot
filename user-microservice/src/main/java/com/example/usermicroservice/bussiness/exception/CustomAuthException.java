package com.example.usermicroservice.bussiness.exception;

public class CustomAuthException extends Exception {
    public CustomAuthException(String message) {
        super(message);
    }
}
