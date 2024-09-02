package com.example.bussiness.validation.validator;

import com.example.bussiness.validation.validator.ValidatorType;

public final class EmailValidator implements ValidatorType<String> {
    public static final EmailValidator VALIDATOR;
    private static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private EmailValidator() {}

    static {
        VALIDATOR = new EmailValidator();
    }

    @Override
    public boolean isValid(String data) {
        return !data.isBlank() && data.matches(emailRegex);
    }
}
