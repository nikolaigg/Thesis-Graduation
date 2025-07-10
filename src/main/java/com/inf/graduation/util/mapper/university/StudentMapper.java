package com.inf.graduation.util.mapper.university;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.dto.student.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    // ViewDTO to UpdateDTO
    @Mapping(target = "user.username", source = "username")
    UpdateStudentDTO toUpdateStudentDTO(StudentAdminViewDTO dto);

    // Entity to DTO
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "username", source = "user.username")
    StudentAdminViewDTO toStudentAdminViewDTO(Student student);

    StudentDropdownDTO toStudentDropdownDTO(Student student);

    @Mapping(target = "departmentName", source = "department.name")
    StudentHomePageDTO toStudentHomePageDTO(Student student);


    // DTO to Entity
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "department", source = "department")
    Student toStudent(CreateStudentDTO dto, Department department);


    void updateStudentFromDto(UpdateStudentDTO dto, @MappingTarget Student student);
}
