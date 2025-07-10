package com.inf.graduation.service.university.impl;

import com.inf.graduation.data.entity.university.TeacherGrade;
import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.university.TeacherGradeRepository;
import com.inf.graduation.service.university.TeacherGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherGradeServiceImpl implements TeacherGradeService {

    private final TeacherGradeRepository teacherGradeRepository;


    @Override
    public void gradeThesis(Thesis thesis, Teacher teacher, int grade) {

        TeacherGrade newGrade = new TeacherGrade();
        newGrade.setThesis(thesis);
        newGrade.setTeacher(teacher);
        newGrade.setGrade(grade);

        teacherGradeRepository.save(newGrade);
    }

    @Override
    public boolean hasTeacherGraded(Long thesisId, Long teacherId) {
        return this.teacherGradeRepository.existsByThesisIdAndTeacherId(thesisId, teacherId);
    }

    @Override
    public Integer getGradeForThesisByTeacher(Long thesisId, Long teacherId) {
        return teacherGradeRepository.findByThesisIdAndTeacherId(thesisId, teacherId)
                .map(TeacherGrade::getGrade).orElse(null);

    }
}

