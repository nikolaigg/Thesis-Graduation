package com.inf.graduation.api.teacher;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.enums.ThesisApplicationStatus;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.student.StudentDropdownDTO;
import com.inf.graduation.dto.thesisApplication.*;
import com.inf.graduation.service.thesis.*;
import com.inf.graduation.service.university.StudentService;
import com.inf.graduation.service.university.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class ThesisApplicationController {

    private final TeacherService teacherService;
    private final ThesisApplicationService thesisApplicationService;
    private final StudentService studentService;


    @GetMapping("/application/my-applications")
    public String myApplications(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<MyThesisApplicationDTO> applications = thesisApplicationService.getThesisApplicationsByTeacher(teacher.getId());

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("myApplications", applications);
        return "teacher/application/my-applications";
    }

    @GetMapping("/create")
    public String showCreateApplicationForm(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<StudentDropdownDTO> students = studentService.getStudentsByDepartmentAndWithoutThesisApplication(teacher.getDepartment().getId());

        model.addAttribute("application", new CreateThesisApplicationDTO());
        model.addAttribute("students", students);

        return "teacher/application/create-application";
    }

    @PostMapping("/create")
    public String createApplication(@Valid @ModelAttribute("application") CreateThesisApplicationDTO dto,
                                    BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "teacher/application/create-application";
        }

        Teacher teacher = LoggedUser.getLoggedInTeacher();

        teacherService.createThesisApplication(teacher.getId(), dto);
        return "redirect:/teacher/application/my-applications";
    }

    @GetMapping("/application/pending-applications")
    public String pendingApplications(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<ThesisApplicationSummaryDTO> applications = thesisApplicationService.getThesisApplicationsByOtherTeachersInDepartment(ThesisApplicationStatus.PENDING,teacher.getDepartment(),teacher);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("applications", applications);
        return "teacher/application/pending-applications";

    }


    @PostMapping("/applications/approve/{id}")
    public String approveApplication(@PathVariable Long id) {
        thesisApplicationService.approveApplication(id);
        return "redirect:/teacher/application/pending-applications";
    }

    @PostMapping("/applications/reject/{id}")
    public String rejectApplication(@PathVariable Long id) {
        thesisApplicationService.rejectApplication(id);
        return "redirect:/teacher/application/pending-applications";
    }


    @GetMapping("/application/approved-applications")
    public String approvedApplications(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<ThesisApplicationSummaryDTO> applications = thesisApplicationService.getThesisApplicationsByOtherTeachers(ThesisApplicationStatus.APPROVED,teacher);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("applications", applications);
        return "teacher/application/approved-applications";

    }

    @GetMapping("/application/view/{id}")
    public String viewApplication(@PathVariable Long id, Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();
        ViewThesisApplicationDetailsDTO application = thesisApplicationService.getApplicationByIdForView(id);

        model.addAttribute("teacher",teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("ViewApplication", application);
        return "teacher/application/view-application";
    }

    @GetMapping("/application/update/{id}")
    public String showApplicationUpdateForm(@PathVariable Long id, Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        UpdateThesisApplicationDTO dto = thesisApplicationService.getApplicationByIdForEdit(id);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("updateApplication", dto);
        model.addAttribute("applicationId", id);

        return "teacher/application/edit-application";
    }

    @PostMapping("/application/update/{id}")
    public String updateApplication(@PathVariable Long id,
                                    @Valid @ModelAttribute("updateApplication") UpdateThesisApplicationDTO dto,
                                    BindingResult bindingResult, Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        if(bindingResult.hasErrors()) {
            model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
            model.addAttribute("applicationId", id);
            return "teacher/application/edit-application";
        }

        thesisApplicationService.updateApplication(dto,id);
        return "redirect:/teacher/application/my-applications";
    }

    @GetMapping("application/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        thesisApplicationService.deleteApplication(id);
        return "redirect:/teacher/application/my-applications";
    }
}
