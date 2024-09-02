package com.example.bussiness.validation.validator;

import com.example.bussiness.validation.validator.ValidatorType;

public final class PhoneValidator implements ValidatorType<String> {
    public static final PhoneValidator VALIDATOR;
    public static final String phoneRegex = "[+]201([1250])[0-9]{8}";

    private PhoneValidator() {
    }

    static {
        VALIDATOR = new PhoneValidator();
    }

    @Override
    public boolean isValid(String data) {
        return data.matches(phoneRegex);
    }
}
