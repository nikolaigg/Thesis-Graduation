package com.inf.graduation.data.repo.university;

import com.inf.graduation.data.entity.university.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
