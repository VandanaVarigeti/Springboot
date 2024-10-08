package com.org.capstone.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrength {

    String message() default "Password must be at least 6 characters long, contain at least 1 symbols, 1 digit, and the rest alphabet characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
