package com.inf.graduation.api.teacher;

import com.inf.graduation.config.LoggedUser;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.student.StudentDropdownDTO;
import com.inf.graduation.dto.teacher.TeacherDropDownDTO;
import com.inf.graduation.dto.thesis.PendingThesisDTO;
import com.inf.graduation.dto.thesisDefense.DefenseSessionSetupDto;
import com.inf.graduation.dto.thesisDefense.DefenseSessionStudentsDto;
import com.inf.graduation.dto.thesisDefense.DefenseSummaryDTO;
import com.inf.graduation.service.thesis.*;
import com.inf.graduation.service.university.TeacherService;
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

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class ThesisDefenseController {

    private final TeacherService teacherService;
    private final ThesisService thesisService;
    private final ThesisDefenseSessionService thesisDefenseSessionService;


    @GetMapping("/theses-pending-defence")
    public String pendingDefence(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<PendingThesisDTO> theses = thesisService.getAllThesesByStatusInDepartment(ThesisStatus.REVIEWED, teacher.getDepartment());

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("theses",theses);
        return "teacher/thesis/defense/theses-pending-defence";
    }

    @GetMapping("/defense/schedule/setup")
    public String showDefenseSessionSetupForm(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<TeacherDropDownDTO> teachers = teacherService.getTeachersByDepartmentId(teacher.getDepartment().getId());
        DefenseSessionSetupDto defense = new DefenseSessionSetupDto();


        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("teachers", teachers);
        model.addAttribute("defense", defense);
        return "teacher/thesis/defense/defense-session-setup";

    }

    @PostMapping("/defense/schedule/setup")
    public String processDefenseSessionSetup(@Valid @ModelAttribute("defense") DefenseSessionSetupDto defense,
                                             BindingResult bindingResult, Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        if (bindingResult.hasErrors()) {
            List<TeacherDropDownDTO> teachers = teacherService.getTeachersByDepartmentId(teacher.getDepartment().getId());

            model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
            model.addAttribute("teachers", teachers);
            return "teacher/thesis/defense/defense-session-setup";
        }

        thesisDefenseSessionService.setCommitteeNames(defense);
        List<StudentDropdownDTO> eligibleStudents = thesisDefenseSessionService.getEligibleStudentsForDefense(defense);
        DefenseSessionStudentsDto defenseStudents = thesisDefenseSessionService.mapToDefenseSessionStudentsDto(defense);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("eligibleStudents", eligibleStudents);
        model.addAttribute("defense", defenseStudents);

        return "teacher/thesis/defense/defense-session-students";
    }

    @PostMapping("/defense/schedule/students")
    public String defenseFinalDetails(@Valid @ModelAttribute("defense") DefenseSessionStudentsDto defense,
                                      BindingResult bindingResult, Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        thesisDefenseSessionService.setStudentNames(defense);

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("defense", defense);
        return "teacher/thesis/defense/defense-session-confirmation";
    }

    @PostMapping("/defense/schedule/confirm")
    public String confirmDefense(@ModelAttribute("defense") DefenseSessionStudentsDto defense) {

        thesisDefenseSessionService.createThesisDefenseSession(defense);
        return "redirect:/teacher/theses-pending-defence";
    }

    @GetMapping("/theses-defenses")
    public String showThesesDefenses(Model model) {
        Teacher teacher = LoggedUser.getLoggedInTeacher();

        List<DefenseSummaryDTO> defensesToday = thesisDefenseSessionService.getDefensesByDateAndTeacherId(LocalDate.now(), teacher.getId());
        List<DefenseSummaryDTO> defensesNotToday = thesisDefenseSessionService.getDefensesNotOnDateAndTeacherId(LocalDate.now(), teacher.getId());

        model.addAttribute("teacher", teacherService.getTeacherHomePageInfo(teacher));
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("defensesToday", defensesToday);
        model.addAttribute("defensesNotToday", defensesNotToday);
        return "teacher/thesis/defense/theses-defenses";
    }
}
