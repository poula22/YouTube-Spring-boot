package com.example.channelmicroservice.controller.model;

public record ChannelDto (
    String name,
    int subscribersCount,
    String category
) {}
