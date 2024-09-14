package com.org.capstone.accountvalidation.validators;

import com.org.capstone.accountvalidation.validatorImpl.ProductTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy =  ProductTypeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductType {


    String message() default "Invalid Product Type and Registration Type";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
