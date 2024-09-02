package com.example.bussiness.validation.validator;

public sealed interface ValidatorType<T>
        permits EmailValidator, PasswordValidator, PhoneValidator {
    boolean isValid(T data);
}
