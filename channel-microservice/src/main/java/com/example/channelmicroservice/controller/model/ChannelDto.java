package com.example.channelmicroservice.controller.model;

public record ChannelDto (
    long id,
    String name,
    long ownerId,
    int subscribersCount
) {}
