package com.inf.graduation.service.university.impl;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.repo.university.DepartmentRepository;
import com.inf.graduation.data.repo.university.StudentRepository;
import com.inf.graduation.data.repo.thesis.ThesisDefenseSessionRepository;
import com.inf.graduation.data.repo.user.UserRepository;
import com.inf.graduation.dto.student.*;
import com.inf.graduation.service.university.StudentService;
import com.inf.graduation.service.user.UserService;
import com.inf.graduation.util.mapper.university.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final ThesisDefenseSessionRepository thesisDefenseSessionRepository;


    @Override
    public List<StudentAdminViewDTO> getStudents() {
        return this.studentRepository.findAll().stream()
                .map(studentMapper::toStudentAdminViewDTO)
                .toList();
    }

    @Override
    public StudentAdminViewDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found!"));

        return studentMapper.toStudentAdminViewDTO(student);
    }

    @Override
    public List<StudentDropdownDTO> getStudentsByIds(Collection<Long> ids) {
        return this.studentRepository.findAllById(ids).stream()
                .map(studentMapper::toStudentDropdownDTO)
                .toList();
    }

    @Override
    public StudentAdminViewDTO createStudent(CreateStudentDTO dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department with id " + dto.getDepartmentId() + " not found!"));

        Student newStudent = studentMapper.toStudent(dto, department);
        User newUser = userService.createUser(dto.getUser(), "ROLE_STUDENT");

        newUser.setUserProfile(newStudent);
        newStudent.setUser(newUser);

        Student savedStudent = studentRepository.save(newStudent);

        return studentMapper.toStudentAdminViewDTO(savedStudent);

    }

    @Override
    public StudentAdminViewDTO updateStudent(UpdateStudentDTO dto, Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found!"));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department with id " + dto.getDepartmentId() + " not found!"));

        studentMapper.updateStudentFromDto(dto, student);
        student.setDepartment(department);
        student.setUser(userService.updateUser(dto.getUser(), id));


        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toStudentAdminViewDTO(updatedStudent);
    }


    @Override
    public void deleteStudent(Long id) {
        User user = userRepository.findByUserProfileId(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found!"));

        Department department = student.getDepartment();
        if (department != null) {
            department.getStudents().remove(student);
        }

        user.getRoles().clear();
        this.userRepository.deleteById(user.getId());

    }

    @Override
    public List<StudentDropdownDTO> getStudentsByDepartment(Long departmentId) {
        return this.studentRepository.findAllStudentsByDepartmentId(departmentId).stream()
                .map(studentMapper::toStudentDropdownDTO)
                .toList();
    }

    @Override
    public List<StudentDropdownDTO> getStudentsByDepartmentAndWithoutThesisApplication(Long departmentId) {
        return this.studentRepository.findByDepartmentIdAndThesisApplicationIsNull(departmentId).stream()
                .map(studentMapper::toStudentDropdownDTO)
                .toList();
    }

    @Override
    public List<StudentDropdownDTO> getEligibleStudentsForDefense(Collection<Long> committeeIds, LocalDate defenseDate) {
        Set<Long> scheduledStudentIds = thesisDefenseSessionRepository.findAllScheduledStudentIds();

        List<Student> students = studentRepository.findByThesisThesisSupervisorIdNotIn(committeeIds);

        return students.stream()
                .filter(s -> !scheduledStudentIds.contains(s.getId()))
                .map(studentMapper::toStudentDropdownDTO)
                .toList();
    }

    @Override
    public StudentHomePageDTO getStudentHomePageInfo(Student student) {
        return this.studentMapper.toStudentHomePageDTO(student);
    }

    @Override
    public Student getById(Long userProfileId) {
        return this.studentRepository.findById(userProfileId)
                .orElseThrow(() -> new RuntimeException("Student with id " + userProfileId + " not found!"));
    }
}