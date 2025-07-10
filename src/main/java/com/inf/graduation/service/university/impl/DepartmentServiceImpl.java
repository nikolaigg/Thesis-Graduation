package com.inf.graduation.service.university.impl;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.university.DepartmentRepository;
import com.inf.graduation.data.repo.university.TeacherRepository;
import com.inf.graduation.dto.department.CreateDepartmentDTO;
import com.inf.graduation.dto.department.DepartmentAdminViewDTO;
import com.inf.graduation.dto.department.UpdateDepartmentDTO;
import com.inf.graduation.service.university.DepartmentService;
import com.inf.graduation.util.mapper.university.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public List<DepartmentAdminViewDTO> getDepartments() {
        return this.departmentRepository.findAll().stream()
                .map(departmentMapper::toDepartmentAdminViewDto)
                .toList();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department with id " + id + " not found"));
    }

    @Override
    public DepartmentAdminViewDTO createDepartment(CreateDepartmentDTO dto) {
        Department newDepartment = departmentMapper.toDepartment(dto);
        Department savedDepartment = departmentRepository.save(newDepartment);
        return departmentMapper.toDepartmentAdminViewDto(savedDepartment);
    }

    @Override
    public DepartmentAdminViewDTO updateDepartment(UpdateDepartmentDTO dto, Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department with id " + id + " not found"));

        departmentMapper.updateDepartmentFromDto(dto, department);

        if (dto.getHeadOfDepartmentId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getHeadOfDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            department.setHeadOfDepartment(teacher);
        }

        Department updatedDepartment = departmentRepository.save(department);
        return departmentMapper.toDepartmentAdminViewDto(updatedDepartment);
    }


    @Override
    public void deleteDepartment(Long id) {
        this.departmentRepository.deleteById(id);
    }
}
