package com.inf.graduation.api.admin;

import com.inf.graduation.dto.teacher.CreateTeacherDTO;
import com.inf.graduation.dto.teacher.TeacherAdminViewDTO;
import com.inf.graduation.dto.teacher.UpdateTeacherDTO;
import com.inf.graduation.dto.user.CreateUserDTO;
import com.inf.graduation.service.university.DepartmentService;
import com.inf.graduation.service.university.TeacherService;
import com.inf.graduation.util.mapper.university.TeacherMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/teachers")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminTeacherController {
    private final TeacherService teacherService;
    private final DepartmentService departmentService;
    private final TeacherMapper teacherMapper;

    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getTeachers());
        return "admin/teachers/teachers";
    }

    @GetMapping("/create")
    public String showCreateTeacherForm(Model model) {
        CreateTeacherDTO createTeacherDTO = new CreateTeacherDTO();
        createTeacherDTO.setUser(new CreateUserDTO());

        model.addAttribute("teacher", createTeacherDTO);
        model.addAttribute("departments", departmentService.getDepartments());

        return "admin/teachers/create-teacher";
    }

    @PostMapping("/create")
    public String createTeacher(@Valid @ModelAttribute("teacher") CreateTeacherDTO createTeacherDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getDepartments());
            return "admin/teachers/create-teacher";
        }
        this.teacherService.createTeacher(createTeacherDTO);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/edit-teacher/{id}")
    public String showEditTeacherForm(@PathVariable Long id, Model model) {
        TeacherAdminViewDTO adminView = teacherService.getTeacherById(id);

        UpdateTeacherDTO updateTeacherDTO = teacherMapper.toUpdateTeacherDTO(adminView);
        updateTeacherDTO.getUser().setCurrentUsername(adminView.getUsername());

        model.addAttribute("teacher", updateTeacherDTO);
        model.addAttribute("teacherId", id);
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("user", updateTeacherDTO.getUser());
        return "admin/teachers/edit-teacher";
    }

    @PostMapping("/edit/{id}")
    public String updateTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("teacher") UpdateTeacherDTO dto,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getDepartments());
            model.addAttribute("teacherId", id);
            return "admin/teachers/edit-teacher";
        }

        this.teacherService.updateTeacher(dto,id);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        this.teacherService.deleteTeacher(id);
        return "redirect:/admin/teachers";
    }
}
