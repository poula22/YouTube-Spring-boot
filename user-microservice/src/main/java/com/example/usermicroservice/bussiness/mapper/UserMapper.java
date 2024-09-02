package com.example.usermicroservice.bussiness.mapper;

import com.example.usermicroservice.domain.entity.UserEntity;
import com.example.security.model.JwtSecurityModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    JwtSecurityModel fromEntityToSecurity(UserEntity userEntity);
}
