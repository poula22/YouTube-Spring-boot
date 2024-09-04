package com.example.usermicroservice.domain.bussiness.mapper;

import com.example.usermicroservice.domain.bussiness.model.signUp.SignUpRequestDto;
import com.example.usermicroservice.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SignUpRequestDtoMapper {
    SignUpRequestDtoMapper INSTANCE = Mappers.getMapper(SignUpRequestDtoMapper.class);

    UserEntity toUserEntity(SignUpRequestDto signUpRequestDto);
}
