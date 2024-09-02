package com.example.bussiness.validation.validator;

public final class PasswordValidator implements ValidatorType<String> {
    public static final PasswordValidator VALIDATOR;
    private static final String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private PasswordValidator() {}

    static {
        VALIDATOR = new PasswordValidator();
    }
    @Override
    public boolean isValid(String data) {
        return data.matches(passwordRegex);
    }
}
