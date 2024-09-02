package com.example.domain.customValidation.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidYoutubeEmailImp.class)
public @interface CustomEmailValidation {
    String message() default "Email is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
