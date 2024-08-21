package com.example.channelmicroservice.mapper;

import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.domain.ChannelEntity;

public class ChannelMapper {
    public static ChannelDto mapToChannelDto(ChannelEntity channel) {
        return new ChannelDto (
                channel.getId(),
                channel.getName(),
                channel.getOwnerId(),
                channel.getSubscribersCount()
        );
    }

    public static ChannelEntity mapToChannelEntity(ChannelDto channelDto) {
        return new ChannelEntity(
                channelDto.id(),
                channelDto.name(),
                channelDto.ownerId(),
                channelDto.subscribersCount()
        );
    }
}
