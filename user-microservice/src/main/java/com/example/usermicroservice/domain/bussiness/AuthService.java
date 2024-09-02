package com.example.usermicroservice.domain.bussiness;

import com.example.domain.entityException.CustomEntityException;
import com.example.usermicroservice.bussiness.exception.CustomAuthException;
import com.example.usermicroservice.bussiness.model.login.LoginRequestDto;
import com.example.usermicroservice.bussiness.model.AuthResponseDto;
import com.example.usermicroservice.bussiness.model.signUp.SignUpRequestDto;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto loginRequest)
            throws CustomAuthException, CustomEntityException;

    AuthResponseDto signUp(SignUpRequestDto user)
            throws CustomAuthException, CustomEntityException;

}
