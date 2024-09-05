package com.example.channelmicroservice.domain.bussiness.model;

public record ChannelRequestDto(
    String name,
    int subscribersCount,
    String category
) {}
