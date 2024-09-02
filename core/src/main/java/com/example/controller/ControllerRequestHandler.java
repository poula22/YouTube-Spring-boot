package com.example.controller;

import com.example.bussiness.model.ApiResponse;
import org.springframework.validation.BindingResult;

import java.util.function.Supplier;

public class ControllerRequestHandler {
    private static ApiResponse.Error onError(BindingResult bindingResult) {
            var error = bindingResult.getAllErrors().get(0);
            return new ApiResponse.Error("400", error.getDefaultMessage());
    }

    private static <T> ApiResponse.Success<T> onSuccess(T response) {
        return new ApiResponse.Success<>(response);
    }

    public static <T> ApiResponse handleRequest(T response, BindingResult bindingResult)
    throws Exception{
        if (bindingResult.hasErrors()) return onError(bindingResult);
        return onSuccess(response);
    }

}
