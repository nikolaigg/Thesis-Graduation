package com.inf.graduation.service.university;

import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.dto.student.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface StudentService {

    List<StudentAdminViewDTO> getStudents();
    StudentAdminViewDTO getStudentById(Long id);
    List<StudentDropdownDTO> getStudentsByIds(Collection<Long> ids);
    StudentAdminViewDTO createStudent(CreateStudentDTO dto);
    StudentAdminViewDTO updateStudent(UpdateStudentDTO dto, Long id);
    void deleteStudent(Long id);

    List<StudentDropdownDTO> getStudentsByDepartment(Long departmentId);
    List<StudentDropdownDTO> getStudentsByDepartmentAndWithoutThesisApplication(Long departmentId);
    List<StudentDropdownDTO> getEligibleStudentsForDefense(Collection<Long> committeeIds, LocalDate defenseDate);
    StudentHomePageDTO getStudentHomePageInfo(Student student);
    Student getById(Long userProfileId);
}

