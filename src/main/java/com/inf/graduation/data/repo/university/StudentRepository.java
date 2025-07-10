package com.inf.graduation.data.repo.university;

import com.inf.graduation.data.entity.university.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllStudentsByDepartmentId(Long departmentId);
    List<Student> findByDepartmentIdAndThesisApplicationIsNull(Long departmentId);
    List<Student> findByThesisThesisSupervisorIdNotIn(Collection<Long> supervisorIds);
    Set<Student> findByIdIn(Collection<Long> ids);
    Student findByThesis_Id(Long thesisId);

}
