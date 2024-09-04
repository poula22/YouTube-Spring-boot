package com.example.videomicroservice.domain.bussiness.model;


public record VideoDto(
        long id,
        long channelId,
        String title,
        String description
) {
}
