package com.inf.graduation.api.student;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.thesisApplication.ThesisApplicationSummaryDTO;
import com.inf.graduation.service.thesis.ThesisApplicationService;
import com.inf.graduation.service.university.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
@RequiredArgsConstructor
public class ThesisApplicationStudentController {

    private final StudentService studentService;
    private final ThesisApplicationService thesisApplicationService;

    @GetMapping("/approved-applications-student-view")
    public String approvedApplications(Model model) {
        Student student = LoggedUser.getLoggedInStudent();

        List<ThesisApplicationSummaryDTO> applications = thesisApplicationService.getApprovedThesisApplicationsInDepartment(ThesisApplicationStatus.APPROVED, student.getDepartment());

        model.addAttribute("student", studentService.getStudentHomePageInfo(student));
        model.addAttribute("applications", applications);
        return "student/approved-application-student-view";

    }
}
