package com.inf.graduation.util.validation.validator;

import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.repo.university.StudentRepository;
import com.inf.graduation.util.validation.annotation.NoActiveThesisApplication;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoActiveThesisApplicationValidator implements ConstraintValidator<NoActiveThesisApplication, Long> {

    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(Long studentId, ConstraintValidatorContext context) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ThesisApplication thesisApplication = student.getThesisApplication();

        if (thesisApplication == null) {

            return true;
        }

        return thesisApplication.getStatus() == ThesisApplicationStatus.REJECTED;
    }

}
