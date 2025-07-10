package com.inf.graduation.util.validation.annotation;

import com.inf.graduation.util.validation.validator.UniqueCommitteeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = UniqueCommitteeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCommitteeMembers{
    String message() default "Cannot have the same committee member more the once";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

