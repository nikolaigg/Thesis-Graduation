package com.inf.graduation.data.repo.university;

import com.inf.graduation.data.entity.university.TeacherGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherGradeRepository extends JpaRepository<TeacherGrade, Long> {
    List<TeacherGrade> findByThesis_Id(Long thesisId);
    boolean existsByThesisIdAndTeacherId(Long thesisId, Long teacherId);
    Optional<TeacherGrade> findByThesisIdAndTeacherId(Long thesisId, Long teacherId);
}
