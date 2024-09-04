package com.example.controller;

import com.example.controller.exception.HttpRequestException;
import org.springframework.validation.BindingResult;

public class ControllerRequestHandler {
    private static void onError(BindingResult bindingResult) throws HttpRequestException {
            var error = bindingResult.getAllErrors().get(0);
            throw new HttpRequestException(error.getDefaultMessage(),400);
    }

    public static void handleRequest(BindingResult bindingResult)
    throws HttpRequestException{
        if (bindingResult!= null && bindingResult.hasErrors()) onError(bindingResult);
    }

}
