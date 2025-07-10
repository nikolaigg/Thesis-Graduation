package com.inf.graduation.util.validation.annotation;

import com.inf.graduation.util.validation.validator.ValidUsernameUpdateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUsernameUpdateValidator.class)
@Documented
public @interface ValidUsernameUpdate {
    String message() default "Username already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}