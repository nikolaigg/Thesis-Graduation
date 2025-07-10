package com.inf.graduation.service.thesis.impl;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.thesis.ThesisRepository;
import com.inf.graduation.data.repo.university.StudentRepository;
import com.inf.graduation.data.repo.university.TeacherRepository;
import com.inf.graduation.data.repo.thesis.ThesisApplicationRepository;
import com.inf.graduation.dto.thesisApplication.*;
import com.inf.graduation.service.thesis.ThesisApplicationService;
import com.inf.graduation.util.mapper.thesis.ThesisApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisApplicationServiceImpl implements ThesisApplicationService {

    private final ThesisApplicationRepository thesisApplicationRepository;
    private final ThesisApplicationMapper thesisApplicationMapper;
    private final ThesisRepository thesisRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;


    @Override
    public List<ThesisApplicationSummaryDTO> getAllApprovedThesisApplications(ThesisApplicationStatus status) {
        return this.thesisApplicationRepository.findByStatus(status).stream()
                .map(thesisApplicationMapper::toThesisApplicationSummaryDTO)
                .toList();
    }

    @Override
    public List<MyThesisApplicationDTO> getThesisApplicationsByTeacher(Long id) {
        return this.thesisApplicationRepository.findByThesisSupervisor_Id(id).stream()
                .map(thesisApplicationMapper::toMyThesisApplicationDTO)
                .toList();
    }

    @Override
    public List<ThesisApplicationSummaryDTO> getThesisApplicationsByOtherTeachers(ThesisApplicationStatus status, Teacher teacher) {
        return this.thesisApplicationRepository.findByStatusAndThesisSupervisorNot(status, teacher).stream()
                .map(thesisApplicationMapper::toThesisApplicationSummaryDTO)
                .toList();
    }

    @Override
    public List<ThesisApplicationSummaryDTO> getThesisApplicationsByOtherTeachersInDepartment(ThesisApplicationStatus status, Department department, Teacher teacher) {
        return this.thesisApplicationRepository.findByStatusAndThesisSupervisor_DepartmentAndThesisSupervisorNot(status, department,teacher).stream()
                .map(thesisApplicationMapper::toThesisApplicationSummaryDTO)
                .toList();
    }

    @Override
    public ViewThesisApplicationDetailsDTO getApplicationByIdForView(Long id) {
        ThesisApplication thesisApplication = thesisApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ThesisApplication not found"));

        return thesisApplicationMapper.toViewThesisApplicationDetailsDTO(thesisApplication);
    }

    @Override
    public UpdateThesisApplicationDTO getApplicationByIdForEdit(Long id) {
        ThesisApplication thesisApplication = thesisApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ThesisApplication not found"));

        return thesisApplicationMapper.toUpdateThesisApplicationDTO(thesisApplication);
    }

    @Override
    public ThesisApplicationGradingViewDTO getApplicationByStudentId(Long id) {
        return this.thesisApplicationMapper.toThesisApplicationGradingViewDTO(thesisApplicationRepository.findByStudent_Id(id));
    }

    @Override
        public ThesisApplication createApplication(CreateThesisApplicationDTO dto, Long teacherId) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow( () -> new RuntimeException("Student Not Found"));
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow( () -> new RuntimeException("Teacher Not Found"));

           ThesisApplication thesisApplication = thesisApplicationMapper.toThesisApplication(dto);
           thesisApplication.setStudent(student);
           student.setThesisApplication(thesisApplication);
           thesisApplication.setThesisSupervisor(teacher);

           return thesisApplicationRepository.save(thesisApplication);
        }

    @Override
    public List<ThesisApplicationSummaryDTO> getApprovedThesisApplicationsInDepartment(ThesisApplicationStatus status, Department department) {
        return this.thesisApplicationRepository.findByStatusAndStudent_Department(status, department).stream()
                .map(thesisApplicationMapper::toThesisApplicationSummaryDTO)
                .toList();
    }

    @Override
    public List<ThesisApplicationSummaryDTO> getApplicationsByTopicContains(String text) {
        return this.thesisApplicationRepository.findByTopicContainingIgnoreCase(text).stream()
                .map(thesisApplicationMapper::toThesisApplicationSummaryDTO)
                .toList();
    }

    @Override
    public List<ThesisApplicationSummaryDTO> getSupervisorApprovedApplications(ThesisApplicationStatus status, Long id) {
        return this.thesisApplicationRepository.findByStatusAndThesisSupervisor_Id(status,id).stream()
                .map(thesisApplicationMapper::toThesisApplicationSummaryDTO)
                .toList();
    }

    @Override
    public void updateApplication(UpdateThesisApplicationDTO dto, Long id) {
        ThesisApplication application = thesisApplicationRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("ThesisApplication not found"));

        thesisApplicationMapper.updateThesisApplication(dto, application);
        application.setStatus(ThesisApplicationStatus.PENDING);
        thesisApplicationRepository.save(application);
    }

    @Override
    public void deleteApplication(Long id) {
        ThesisApplication app = thesisApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Student student = app.getStudent();

        student.setThesisApplication(null);

        thesisApplicationRepository.delete(app);
    }
    @Override
    public void approveApplication(Long id) {
        ThesisApplication thesisApplication = thesisApplicationRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Thesis Application Not Found"));

        thesisApplication.setStatus(ThesisApplicationStatus.APPROVED);
        thesisApplicationRepository.save(thesisApplication);

    }

    @Override
    public void rejectApplication(Long id) {
        ThesisApplication thesisApplication = thesisApplicationRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Thesis Application Not Found"));

        thesisApplication.setStatus(ThesisApplicationStatus.REJECTED);
        thesisApplicationRepository.save(thesisApplication);
    }
}
