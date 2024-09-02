package com.example.channelmicroservice.bussiness.mapper;

import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.domain.entity.ChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelMapper {
    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    ChannelEntity fromDtoToEntity(ChannelDto channelDto, long ownerId);
    ChannelDto fromEntityToDto(ChannelEntity channelEntity);
}
