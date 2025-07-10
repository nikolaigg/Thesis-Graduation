package com.inf.graduation.data.repo.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {

    List<Thesis> findByThesisSupervisorId(Long teacherId);
    List<Thesis> findByReview_StatusAndThesisSupervisor_DepartmentAndThesisSupervisorNot(ThesisReviewAssessment status, Department department, Teacher teacher);
    List<Thesis> findByStatusAndThesisSupervisor_Department(ThesisStatus status, Department department );
    Thesis findByStudentId(Long studentId);
}
