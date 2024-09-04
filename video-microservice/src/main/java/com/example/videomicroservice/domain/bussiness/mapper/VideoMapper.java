package com.example.videomicroservice.domain.bussiness.mapper;

import com.example.videomicroservice.domain.bussiness.model.VideoDto;
import com.example.videomicroservice.domain.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface VideoMapper {
    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);
    VideoDto fromEntityToDto(VideoEntity videoEntity);
    VideoEntity fromDtoToEntity(VideoDto videoDto, String url);
}
