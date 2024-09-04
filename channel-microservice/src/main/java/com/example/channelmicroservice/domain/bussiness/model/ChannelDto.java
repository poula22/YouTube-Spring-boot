package com.example.channelmicroservice.domain.bussiness.model;

public record ChannelDto (
    String name,
    int subscribersCount,
    String category
) {}
