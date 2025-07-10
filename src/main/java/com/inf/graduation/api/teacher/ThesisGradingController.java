package com.inf.graduation.api.teacher;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.thesis.GradedThesisDTO;
import com.inf.graduation.dto.thesis.ThesisGradingDTO;
import com.inf.graduation.dto.thesisApplication.ThesisApplicationGradingViewDTO;
import com.inf.graduation.dto.thesisReview.ReviewGradingDTO;
import com.inf.graduation.service.thesis.*;
import com.inf.graduation.service.university.TeacherGradeService;
import com.inf.graduation.service.university.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class ThesisGradingController {

    private final TeacherService teacherService;
    private final ThesisApplicationService thesisApplicationService;
    private final ThesisService thesisService;
    private final ThesisReviewService thesisReviewService;
    private final TeacherGradeService teacherGradeService;
    private final ThesisGradeService thesisGradeService;

    @GetMapping("/grade/{id}")
    public String showGradingForm(Model model, @PathVariable Long id) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        Thesis thesis = thesisService.getThesisById(id);

        ThesisApplicationGradingViewDTO application = thesisApplicationService.getApplicationByStudentId(thesis.getStudent().getId());
        ThesisGradingDTO thesisDto = thesisService.mapToGradingDTO(thesis);
        ReviewGradingDTO review = thesisReviewService.getReviewByThesisId(thesis.getId());

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("thesisApplication", application);
        model.addAttribute("thesis", thesisDto);
        model.addAttribute("review", review);
        return "teacher/thesis/grading/grading";
    }

    @PostMapping("/thesis/grade/{id}")
    public String gradeThesis(@PathVariable Long id, @RequestParam("grade") int grade) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();
        Thesis thesis = thesisService.getThesisById(id);

        teacherGradeService.gradeThesis(thesis,teacher,grade);

        if(thesisGradeService.allCommitteeMembersGraded(thesis.getId())){
            thesisGradeService.submitFinalGrade(thesis.getId());
        }
        return "redirect:/teacher/theses-defenses";
    }

    @GetMapping("/thesis/grading/graded-theses")
    public String gradedTheses(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<GradedThesisDTO> gradedTheses = thesisGradeService.getGradedTheses();

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("gradedTheses", gradedTheses);

        return "teacher/thesis/grading/graded-theses";
    }
}
