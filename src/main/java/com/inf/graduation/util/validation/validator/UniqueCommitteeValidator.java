package com.inf.graduation.util.validation.validator;

import com.inf.graduation.util.validation.annotation.UniqueCommitteeMembers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueCommitteeValidator implements ConstraintValidator<UniqueCommitteeMembers, List<Long>> {

    @Override
    public boolean isValid(List<Long> list, ConstraintValidatorContext context) {
        if (list == null) return true;

        Set<Long> seen = new HashSet<>();
        for (Long id : list) {
            if (id == null) return false;
            if (!seen.add(id)) {
                return false;
            }
        }

        return true;
    }
}
