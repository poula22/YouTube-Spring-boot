package com.example.usermicroservice.bussiness.model.signUp;


import com.example.domain.customValidation.email.CustomEmailValidation;
import com.example.domain.customValidation.password.CustomPasswordValidation;

public record SignUpRequestDto(
        String name,
        @CustomPasswordValidation
        String password,
        @CustomEmailValidation
        String email
) {
}
