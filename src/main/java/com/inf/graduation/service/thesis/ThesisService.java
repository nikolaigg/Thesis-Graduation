package com.inf.graduation.service.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.thesis.*;

import java.util.List;

public interface ThesisService {

    PendingThesisDTO getPendingThesisById(Long id);
    List<ThesisSupervisionsTeacherViewDTO> getAllThesesByTeacherId(Long teacherId);
    List<PendingThesisDTO> getAllThesesByOtherTeachersInDepartment(ThesisReviewAssessment status, Department department, Teacher teacher);
    List<PendingThesisDTO> getAllThesesByStatusInDepartment(ThesisStatus status, Department department);
    Thesis getThesisById(Long id);
    void uploadThesis(UploadThesisDTO dto, Long id);
    ThesisGradingDTO mapToGradingDTO(Thesis thesis);
    ThesisStudentViewDTO getThesisOfStudent(Long id);

}
