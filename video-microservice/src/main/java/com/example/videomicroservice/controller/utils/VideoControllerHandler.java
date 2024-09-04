package com.example.videomicroservice.controller.utils;

import com.example.controller.exception.HttpRequestException;
import com.example.videomicroservice.domain.bussiness.exception.VideoException;

public interface VideoControllerHandler<T>  {
    T handle() throws VideoException, HttpRequestException;
}