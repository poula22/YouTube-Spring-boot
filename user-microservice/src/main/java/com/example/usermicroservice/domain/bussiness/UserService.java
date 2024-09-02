package com.example.usermicroservice.domain.bussiness;

import com.example.domain.entityException.CustomEntityException;
import com.example.usermicroservice.domain.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity findUserById(long id) throws CustomEntityException;
    UserEntity saveAndUpdateUserInfo(UserEntity userEntity) throws CustomEntityException;
}
