package com.inf.graduation.util.validation.validator;

import com.inf.graduation.data.repo.user.UserRepository;
import com.inf.graduation.dto.user.UpdateUserDTO;
import com.inf.graduation.util.validation.annotation.ValidUsernameUpdate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidUsernameUpdateValidator implements ConstraintValidator<ValidUsernameUpdate, UpdateUserDTO> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UpdateUserDTO dto, ConstraintValidatorContext context) {

        if (dto.getUsername().equals(dto.getCurrentUsername())) {
            return true;
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username is already taken")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}