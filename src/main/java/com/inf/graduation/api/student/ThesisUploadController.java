package com.inf.graduation.api.student;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.ThesisGrade;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.dto.student.StudentHomePageDTO;
import com.inf.graduation.dto.thesis.ThesisStudentViewDTO;
import com.inf.graduation.dto.thesis.UploadThesisDTO;
import com.inf.graduation.dto.thesisApplication.StudentThesisApplicationDetailsDTO;
import com.inf.graduation.service.thesis.ThesisGradeService;
import com.inf.graduation.service.university.StudentService;
import com.inf.graduation.service.thesis.ThesisService;
import com.inf.graduation.util.mapper.university.StudentMapper;
import com.inf.graduation.util.mapper.thesis.ThesisApplicationMapper;
import com.inf.graduation.util.mapper.thesis.ThesisMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
@RequiredArgsConstructor
public class ThesisUploadController {

    private final StudentMapper studentMapper;
    private final ThesisApplicationMapper thesisApplicationMapper;
    private final ThesisService thesisService;
    private final ThesisGradeService thesisGradeService;



    @GetMapping("/my-thesis")
    public String myThesis(Model model) {
        Student student = LoggedUser.getLoggedInStudent();

        StudentHomePageDTO dto = studentMapper.toStudentHomePageDTO(student);
        StudentThesisApplicationDetailsDTO application = thesisApplicationMapper.toStudentThesisApplicationDetailsDTO(student.getThesisApplication());
        ThesisStudentViewDTO thesis = thesisService.getThesisOfStudent(student.getId());

        BigDecimal grade = null;
        if (student.getThesis() != null) {
            ThesisGrade thesisGrade = thesisGradeService.getGradeByThesis(student.getThesis().getId());
            if (thesisGrade != null) {
                grade = thesisGrade.getFinalGrade();
            }
        }

        if (thesis != null) {
            thesis.setGrade(grade);
        }

        model.addAttribute("student", dto);
        model.addAttribute("studentApplication", application);
        model.addAttribute("thesis", thesis);
        return "student/my-thesis";
    }

    @GetMapping("/thesis/upload")
    public String showUploadThesisForm(Model model) {
        model.addAttribute("thesis", new UploadThesisDTO());
        return "student/upload-thesis";
    }

    @PostMapping("/thesis/upload")
    public String uploadThesis(@Valid @ModelAttribute("thesis") UploadThesisDTO dto,
                               BindingResult bindingResult) {

        Student student = LoggedUser.getLoggedInStudent();

        if (bindingResult.hasErrors()) {
            return "student/upload-thesis";
        }

        thesisService.uploadThesis(dto, student.getId());

        return "redirect:/student/my-thesis";
    }
}
