package com.inf.graduation.util.mapper.university;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.teacher.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    // ViewDTO to UpdateDTO
    @Mapping(target = "user.username", source = "username")
    UpdateTeacherDTO toUpdateTeacherDTO(TeacherAdminViewDTO dto);

    // Entity to DTO
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "username", source = "user.username")
    TeacherAdminViewDTO toTeacherAdminViewDTO(Teacher teacher);

    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "headOfDepartment", ignore = true)
    TeacherHomePageDTO toTeacherHomePageDTO(Teacher teacher);

    TeacherDropDownDTO toTeacherDropDownDTO(Teacher teacher);

    // DTO to Entity
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "department", source = "department")
    Teacher toTeacher(CreateTeacherDTO dto, Department department);

    void updateTeacherFromDto(UpdateTeacherDTO dto, @MappingTarget Teacher teacher);

}
