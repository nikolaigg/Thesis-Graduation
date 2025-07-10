package com.inf.graduation.service.university;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.dto.department.CreateDepartmentDTO;
import com.inf.graduation.dto.department.DepartmentAdminViewDTO;
import com.inf.graduation.dto.department.UpdateDepartmentDTO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentAdminViewDTO> getDepartments();
    Department getDepartmentById(Long id);
    DepartmentAdminViewDTO createDepartment(CreateDepartmentDTO dto);
    DepartmentAdminViewDTO updateDepartment(UpdateDepartmentDTO dto, Long id);
    void deleteDepartment(Long id);
}
