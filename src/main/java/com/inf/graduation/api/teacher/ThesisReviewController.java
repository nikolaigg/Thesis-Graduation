package com.inf.graduation.api.teacher;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.thesis.PendingThesisDTO;
import com.inf.graduation.dto.thesisReview.WriteThesisReviewDTO;
import com.inf.graduation.service.thesis.*;
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
public class ThesisReviewController {

    private final ThesisService thesisService;
    private final ThesisReviewService thesisReviewService;
    private final TeacherService teacherService;

    @GetMapping("/theses-pending-review")
    public String thesesPendingReview(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<PendingThesisDTO> theses = thesisService.getAllThesesByOtherTeachersInDepartment(ThesisReviewAssessment.PENDING, teacher.getDepartment(),teacher);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("theses",theses);
        return "teacher/thesis/review/theses-pending-review";
    }
    @GetMapping("/thesis/review/{id}")
    public String showReviewThesisForm(@PathVariable Long id, Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        PendingThesisDTO thesis = thesisService.getPendingThesisById(id);
        WriteThesisReviewDTO reviewDto = new WriteThesisReviewDTO();
        reviewDto.setThesisId(id);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("review", reviewDto);
        model.addAttribute("thesis", thesis);
        return "teacher/thesis/review/review-thesis";
    }

    @PostMapping("/thesis/review")
    public String reviewThesis(@Valid @ModelAttribute("review") WriteThesisReviewDTO dto,
                               BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "teacher/thesis/review/review-thesis";
        }

        thesisReviewService.writeThesisReview(dto);
        return "redirect:/teacher/theses-pending-review";
    }
}
