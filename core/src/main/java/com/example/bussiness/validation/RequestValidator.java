package com.example.bussiness.validation;

import com.example.bussiness.validation.validator.ValidatorType;

public abstract class RequestValidator {
    public static <T> boolean validate(T data, ValidatorType<T> validatorType) {
        return validatorType.isValid(data);
    }
}
