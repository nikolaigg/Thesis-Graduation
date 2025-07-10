package com.inf.graduation.data.repo.university;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByDepartmentId(Long departmentId);

    Set<Teacher> findByIdIn(Collection<Long> ids);
}
