package com.inf.graduation.util.mapper.university;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.dto.department.CreateDepartmentDTO;
import com.inf.graduation.dto.department.DepartmentAdminViewDTO;
import com.inf.graduation.dto.department.UpdateDepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    // Entity to DTO
    @Mapping(target = "headOfDepartmentId", source = "headOfDepartment.id")
    @Mapping(target = "headOfDepartmentName", source = "headOfDepartment.name")
    DepartmentAdminViewDTO toDepartmentAdminViewDto(Department department);

    @Mapping(target = "headOfDepartmentId", source = "headOfDepartment.id")
    UpdateDepartmentDTO toUpdateDepartmentDTO(Department department);

    // DTO to Entity
    Department toDepartment(CreateDepartmentDTO dto);

    void updateDepartmentFromDto(UpdateDepartmentDTO dto, @MappingTarget Department department);
}

