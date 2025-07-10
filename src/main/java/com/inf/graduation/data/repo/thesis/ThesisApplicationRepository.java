package com.inf.graduation.data.repo.thesis;

import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesisApplicationRepository extends JpaRepository<ThesisApplication, Long> {

    List<ThesisApplication> findByThesisSupervisor_Id(Long id);
    List<ThesisApplication> findByStatusAndThesisSupervisorNot(ThesisApplicationStatus status, Teacher teacher);
    List<ThesisApplication> findByStatusAndThesisSupervisor_DepartmentAndThesisSupervisorNot(ThesisApplicationStatus status, Department department, Teacher teacher);
    List<ThesisApplication> findByStatusAndStudent_Department(ThesisApplicationStatus status, Department department);
    List<ThesisApplication> findByStatus(ThesisApplicationStatus status);
    ThesisApplication findByStudent_Id(Long studentId);
    List<ThesisApplication> findByTopicContainingIgnoreCase(String text);
    List<ThesisApplication> findByStatusAndThesisSupervisor_Id(ThesisApplicationStatus status, Long id);
}
