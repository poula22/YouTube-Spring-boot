package com.example.domain.customValidation.password;

import com.example.bussiness.validation.RequestValidator;
import com.example.bussiness.validation.validator.EmailValidator;
import com.example.bussiness.validation.validator.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomPasswordValidationImp implements ConstraintValidator<CustomPasswordValidation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return RequestValidator.validate(value, PasswordValidator.VALIDATOR);
    }
}
