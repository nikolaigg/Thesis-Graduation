package com.inf.graduation.data.repo.thesis;

import com.inf.graduation.data.entity.thesis.ThesisGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ThesisGradeRepository extends JpaRepository<ThesisGrade, Long> {

    Long countByFinalGradeGreaterThanAndThesis_ThesisSupervisor_Id(BigDecimal grade, Long teacherId);
    ThesisGrade findByThesisId(Long thesisId);
}
