package com.inf.graduation.api.teacher;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.thesis.PendingThesisDTO;
import com.inf.graduation.dto.thesis.ThesisSupervisionsTeacherViewDTO;
import com.inf.graduation.service.thesis.*;
import com.inf.graduation.service.university.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class ThesisSupervisionController {

    private final ThesisService thesisService;
    private final TeacherService teacherService;

    @GetMapping("/my-supervisions")
    public String mySupervisions(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<ThesisSupervisionsTeacherViewDTO> theses = thesisService.getAllThesesByTeacherId(teacher.getId());

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("theses",theses);
        return "teacher/my-supervisions";
    }


}
