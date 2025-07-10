package com.inf.graduation.service.university.impl;

import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.repo.university.DepartmentRepository;
import com.inf.graduation.data.repo.university.TeacherRepository;
import com.inf.graduation.data.repo.user.UserRepository;
import com.inf.graduation.dto.teacher.*;
import com.inf.graduation.dto.thesisApplication.CreateThesisApplicationDTO;
import com.inf.graduation.service.university.TeacherService;
import com.inf.graduation.service.thesis.ThesisApplicationService;
import com.inf.graduation.service.user.UserService;
import com.inf.graduation.util.mapper.university.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ThesisApplicationService thesisApplicationService;

    @Override
    public List<TeacherAdminViewDTO> getTeachers() {
        return this.teacherRepository.findAll().stream()
                .map(teacherMapper::toTeacherAdminViewDTO)
                .toList();
    }

    @Override
    public List<TeacherDropDownDTO> getTeachersByDepartmentId(Long departmentId) {
        return this.teacherRepository.findByDepartmentId(departmentId).stream()
                .map(teacherMapper::toTeacherDropDownDTO)
                .toList();
    }

    @Override
    public List<TeacherDropDownDTO> getTeachersByIds(List<Long> ids) {
        return this.teacherRepository.findAllById(ids).stream()
                .map(teacherMapper::toTeacherDropDownDTO)
                .toList();
    }

    @Override
    public List<Teacher> getTeacherByDepartmentId(Long departmentId) {
        return this.teacherRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<TeacherDropDownDTO> getAllTeachers() {
        return this.teacherRepository.findAll().stream()
                .map(teacherMapper::toTeacherDropDownDTO)
                .toList();
    }

    @Override
    public TeacherAdminViewDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Teacher with id " + id + " not found!" ));

        return teacherMapper.toTeacherAdminViewDTO(teacher);
    }

    @Override
    public TeacherAdminViewDTO createTeacher(CreateTeacherDTO dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department with id " + dto.getDepartmentId() + " not found!" ));

        Teacher newTeacher = teacherMapper.toTeacher(dto,department);
        User newUser = userService.createUser(dto.getUser(),"ROLE_TEACHER");

        newUser.setUserProfile(newTeacher);
        newTeacher.setUser(newUser);

        Teacher savedTeacher = teacherRepository.save(newTeacher);

        return teacherMapper.toTeacherAdminViewDTO(savedTeacher);
    }

    @Override
    public TeacherAdminViewDTO updateTeacher(UpdateTeacherDTO dto, Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher with id " + id + " not found!" ));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department with id " + dto.getDepartmentId() + " not found!" ));

        teacherMapper.updateTeacherFromDto(dto,teacher);
        teacher.setDepartment(department);
        teacher.setUser(userService.updateUser(dto.getUser(),id));


        Teacher updatedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherAdminViewDTO(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        User user = userRepository.findByUserProfileId(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found!" ));

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher with id " + id + " not found!" ));

        Department department = teacher.getDepartment();
        if(department != null) {
            department.getTeachers().remove(teacher);
        }

        user.getRoles().clear();
        this.userRepository.deleteById(user.getId());
    }

    @Override
    public ThesisApplication createThesisApplication(Long teacherId, CreateThesisApplicationDTO dto) {
       return this.thesisApplicationService.createApplication(dto, teacherId);
    }

    @Override
    public TeacherHomePageDTO getTeacherHomePageInfo(Teacher teacher) {
        TeacherHomePageDTO teacherHomePageDTO = teacherMapper.toTeacherHomePageDTO(teacher);

        boolean isHead = teacher.getDepartment().getHeadOfDepartment() != null
                && teacher.getDepartment().getHeadOfDepartment().getId().equals(teacher.getId());

        teacherHomePageDTO.setHeadOfDepartment(isHead);
        return teacherHomePageDTO;
    }
}
