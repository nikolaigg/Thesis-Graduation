package com.inf.graduation.service.university;

import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.teacher.*;
import com.inf.graduation.dto.thesisApplication.CreateThesisApplicationDTO;

import java.util.List;

public interface TeacherService {

    List<TeacherAdminViewDTO> getTeachers();
    List<TeacherDropDownDTO> getTeachersByDepartmentId(Long departmentId);
    List<TeacherDropDownDTO> getTeachersByIds(List<Long> ids);
    List<Teacher> getTeacherByDepartmentId(Long departmentId);
    List<TeacherDropDownDTO> getAllTeachers();
    TeacherAdminViewDTO getTeacherById(Long id);
    TeacherAdminViewDTO createTeacher(CreateTeacherDTO dto);
    TeacherAdminViewDTO updateTeacher(UpdateTeacherDTO dto, Long id);
    void deleteTeacher(Long id);

    ThesisApplication createThesisApplication(Long teacherId, CreateThesisApplicationDTO dto);
    TeacherHomePageDTO getTeacherHomePageInfo(Teacher teacher);
}
