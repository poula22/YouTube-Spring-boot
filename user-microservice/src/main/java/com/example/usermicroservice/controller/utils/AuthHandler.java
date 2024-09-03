package com.example.usermicroservice.controller.utils;

import com.example.controller.exception.HttpRequestException;
import com.example.domain.entityException.CustomEntityException;
import com.example.usermicroservice.bussiness.exception.CustomAuthException;

@FunctionalInterface
public interface AuthHandler<T> {
    T handle() throws HttpRequestException, CustomAuthException, CustomEntityException;
}
