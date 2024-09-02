package com.example.usermicroservice.bussiness.model.login;

import com.example.domain.customValidation.password.CustomPasswordValidation;

public record LoginRequestDto(
        String userName,
        @CustomPasswordValidation
        String password
) {
}
