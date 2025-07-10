package com.inf.graduation.api.report;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.user.UserProfile;
import com.inf.graduation.dto.teacher.TeacherDropDownDTO;
import com.inf.graduation.dto.thesisApplication.ThesisApplicationSummaryDTO;
import com.inf.graduation.service.thesis.ThesisApplicationService;
import com.inf.graduation.service.thesis.ThesisGradeService;
import com.inf.graduation.service.university.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
public class ReportController {

    private final ThesisApplicationService thesisApplicationService;
    private final TeacherService teacherService;
    private final ThesisGradeService thesisGradeService;

    @GetMapping
    public String showReportsPage(Model model) {
        List<TeacherDropDownDTO> teachersList = teacherService.getAllTeachers();

        model.addAttribute("teacherList", teachersList);
        model.addAttribute("backLink", getBackLink());
        return "reports/reports";
    }


    @PostMapping("/approved")
    public String showApprovedApplicationsReport(Model model) {
        List<TeacherDropDownDTO> teachersList = teacherService.getAllTeachers();
        List<ThesisApplicationSummaryDTO> approvedTheses = thesisApplicationService.getAllApprovedThesisApplications(ThesisApplicationStatus.APPROVED);

        model.addAttribute("teacherList", teachersList);
        model.addAttribute("approvedTheses", approvedTheses);
        model.addAttribute("backLink", getBackLink());
        return "reports/reports";
    }

    @PostMapping("/approved-and-supervisor")
    public String showSupervisorApprovedApplicationsReport(Model model, @RequestParam Long teacherId) {
        List<TeacherDropDownDTO> teachersList = teacherService.getAllTeachers();

        List<ThesisApplicationSummaryDTO> supervisorApprovedTheses = thesisApplicationService
                .getSupervisorApprovedApplications(ThesisApplicationStatus.APPROVED, teacherId);

        model.addAttribute("supervisorApprovedTheses", supervisorApprovedTheses);
        model.addAttribute("teacherList", teachersList);
        model.addAttribute("backLink", getBackLink());

        return "reports/reports";
    }

    @PostMapping("/count-grade")
    public String showStudentsWithGradeAbove(Model model, @RequestParam Long teacherId) {
        List<TeacherDropDownDTO> teachersList = teacherService.getAllTeachers();

        Long count = thesisGradeService.getStudentCountWithGradeAboveBySupervisor(teacherId);
        model.addAttribute("teacherList", teachersList);
        model.addAttribute("studentsCount", count);
        model.addAttribute("backLink", getBackLink());

        return "reports/reports";
    }

    private String getBackLink() {
        UserProfile user = LoggedUser.getLoggedInUserProfile();
        String backLink;

        if(user instanceof Student) {
            backLink = "/student/my-thesis";
        }
        else{
            backLink = "/teacher/application/my-applications";
        }

        return backLink;
    }
}
