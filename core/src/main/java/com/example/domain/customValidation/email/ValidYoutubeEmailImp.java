package com.example.domain.customValidation.email;

import com.example.bussiness.validation.RequestValidator;
import com.example.bussiness.validation.validator.EmailValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidYoutubeEmailImp implements ConstraintValidator<CustomEmailValidation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return RequestValidator.validate(value, EmailValidator.VALIDATOR);
    }
}
