package com.inf.graduation.service.thesis;

import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.thesisApplication.*;

import java.util.List;

public interface ThesisApplicationService {

    List<ThesisApplicationSummaryDTO> getAllApprovedThesisApplications(ThesisApplicationStatus status);

    // maybe fix parameters
    List<MyThesisApplicationDTO> getThesisApplicationsByTeacher(Long id);
    List<ThesisApplicationSummaryDTO> getThesisApplicationsByOtherTeachers(ThesisApplicationStatus status, Teacher teacher);
    List<ThesisApplicationSummaryDTO> getThesisApplicationsByOtherTeachersInDepartment(ThesisApplicationStatus status, Department department, Teacher teacher);

    ViewThesisApplicationDetailsDTO getApplicationByIdForView(Long id);
    UpdateThesisApplicationDTO getApplicationByIdForEdit(Long id);
    ThesisApplicationGradingViewDTO getApplicationByStudentId(Long id);
    ThesisApplication createApplication(CreateThesisApplicationDTO dto, Long teacherId);
    List<ThesisApplicationSummaryDTO> getApprovedThesisApplicationsInDepartment(ThesisApplicationStatus status, Department department);
    List<ThesisApplicationSummaryDTO> getApplicationsByTopicContains(String text);
    List<ThesisApplicationSummaryDTO> getSupervisorApprovedApplications(ThesisApplicationStatus status, Long id);
    void updateApplication(UpdateThesisApplicationDTO dto, Long id);
    void deleteApplication(Long id);

    void approveApplication(Long id);
    void rejectApplication(Long id);

}
