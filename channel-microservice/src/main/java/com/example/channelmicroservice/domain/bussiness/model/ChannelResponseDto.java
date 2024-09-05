package com.example.channelmicroservice.domain.bussiness.model;

import com.example.channelmicroservice.domain.entity.VideoEntity;

import java.util.List;

public record ChannelResponseDto (
        long id,
        String name,
        long ownerId,
        int subscribersCount,
        String category,
        List<VideoEntity> videoEntityList
) {}
