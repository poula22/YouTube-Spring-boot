package com.example.channelmicroservice.controller.utils;

import com.example.channelmicroservice.bussiness.exception.ChannelException;
@FunctionalInterface
public interface ChannelHandler<T> {
    T handle() throws ChannelException;
}
