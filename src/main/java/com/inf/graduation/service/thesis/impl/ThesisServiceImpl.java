package com.inf.graduation.service.thesis.impl;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisReview;
import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.university.StudentRepository;
import com.inf.graduation.data.repo.university.TeacherRepository;
import com.inf.graduation.data.repo.thesis.ThesisRepository;
import com.inf.graduation.dto.thesis.*;
import com.inf.graduation.service.thesis.ThesisGradeService;
import com.inf.graduation.service.thesis.ThesisService;
import com.inf.graduation.util.mapper.thesis.ThesisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisServiceImpl implements ThesisService {

    private final ThesisRepository thesisRepository;
    private final ThesisMapper thesisMapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public PendingThesisDTO getPendingThesisById(Long id) {
        Thesis thesis = thesisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thesis not found"));

        return thesisMapper.toPendingThesisDTO(thesis);
    }

    @Override
    public List<ThesisSupervisionsTeacherViewDTO> getAllThesesByTeacherId(Long teacherId) {
        return thesisRepository.findByThesisSupervisorId(teacherId).stream()
                .map(thesisMapper::toThesisSupervisionsTeacherViewDTO)
                .toList();
    }

    @Override
    public List<PendingThesisDTO> getAllThesesByOtherTeachersInDepartment(ThesisReviewAssessment status, Department department, Teacher teacher) {
        return thesisRepository.findByReview_StatusAndThesisSupervisor_DepartmentAndThesisSupervisorNot(status,department,teacher).stream()
                .map(thesisMapper::toPendingThesisDTO)
                .toList();
    }

    @Override
    public List<PendingThesisDTO> getAllThesesByStatusInDepartment(ThesisStatus status, Department department) {
        return thesisRepository.findByStatusAndThesisSupervisor_Department(status, department).stream()
                .map(thesisMapper::toPendingThesisDTO)
                .toList();
    }

    @Override
    public Thesis getThesisById(Long id) {
        return this.thesisRepository.findById(id).orElseThrow(() -> new RuntimeException("Thesis not found"));
    }

    @Override
    public void uploadThesis(UploadThesisDTO dto, Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        Long teacherId = student.getThesisApplication().getThesisSupervisor().getId();

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found"));

        Thesis thesis = thesisMapper.toThesis(dto);
        ThesisReview review = new ThesisReview();

        thesis.setUploadDate(LocalDate.now());
        thesis.setStudent(student);
        thesis.setThesisSupervisor(teacher);

        thesis.setReview(review);
        review.setThesisReviewed(thesis);
        thesis.getReview().setStatus(ThesisReviewAssessment.PENDING);

        thesis.setStatus(ThesisStatus.PENDING_REVIEW);

        thesisRepository.save(thesis);
    }

    @Override
    public ThesisGradingDTO mapToGradingDTO(Thesis thesis) {
        return this.thesisMapper.toThesisGradingDTO(thesis);
    }

    @Override
    public ThesisStudentViewDTO getThesisOfStudent(Long id) {
        return this.thesisMapper.toThesisStudentViewDTO(
                this.thesisRepository.findByStudentId(id)
        );
    }
}
