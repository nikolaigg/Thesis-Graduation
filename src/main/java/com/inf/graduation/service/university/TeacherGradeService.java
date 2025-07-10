package com.inf.graduation.service.university;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.university.Teacher;

public interface TeacherGradeService {
    void gradeThesis(Thesis thesis, Teacher teacher, int grade);
    boolean hasTeacherGraded(Long thesisId, Long teacherId);

    Integer getGradeForThesisByTeacher(Long thesisId, Long teacherId);
}
