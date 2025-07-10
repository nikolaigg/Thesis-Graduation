package com.inf.graduation.util.validation.annotation;

import com.inf.graduation.util.validation.validator.NoActiveThesisApplicationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoActiveThesisApplicationValidator.class)
@Documented
public @interface NoActiveThesisApplication {
    String message() default "Student already has a pending or approved application.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
