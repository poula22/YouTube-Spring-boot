package com.example.channelmicroservice.domain.bussiness.mapper;

import com.example.channelmicroservice.domain.bussiness.model.ChannelRequestDto;
import com.example.channelmicroservice.domain.bussiness.model.ChannelResponseDto;
import com.example.channelmicroservice.domain.entity.ChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelMapper {
    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    ChannelEntity fromDtoToEntity(ChannelRequestDto channelDto, long ownerId);
    ChannelResponseDto fromEntityToDto(ChannelEntity channelEntity);
}