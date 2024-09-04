package com.example.channelmicroservice.controller.utils;

import com.example.channelmicroservice.domain.bussiness.exception.ChannelException;
import com.example.controller.exception.HttpRequestException;

@FunctionalInterface
public interface ChannelHandler<T> {
    T handle() throws ChannelException, HttpRequestException;
}
