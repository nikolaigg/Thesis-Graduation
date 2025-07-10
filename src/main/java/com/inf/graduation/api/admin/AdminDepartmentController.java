package com.inf.graduation.api.admin;

import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.dto.department.CreateDepartmentDTO;
import com.inf.graduation.dto.department.UpdateDepartmentDTO;
import com.inf.graduation.dto.teacher.TeacherDropDownDTO;
import com.inf.graduation.service.university.DepartmentService;
import com.inf.graduation.service.university.TeacherService;
import com.inf.graduation.util.mapper.university.DepartmentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/departments")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class    AdminDepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final TeacherService teacherService;

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        return "admin/departments/departments";
    }

    @GetMapping("/create")
    public String showCreateDepartmentForm(Model model) {
        model.addAttribute("department", new CreateDepartmentDTO());
        return "admin/departments/create-department";
    }

    @PostMapping("/create")
    public String createDepartment(@Valid @ModelAttribute("department") CreateDepartmentDTO createDepartmentDTO,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/departments/create-department";
        }

        this.departmentService.createDepartment(createDepartmentDTO);
        return "redirect:/admin/departments";

    }

    @GetMapping("/edit-department/{id}")
    public String showEditDepartmentForm(Model model, @PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        UpdateDepartmentDTO updateDto = departmentMapper.toUpdateDepartmentDTO(department);

        List<TeacherDropDownDTO> teachers = teacherService.getTeachersByDepartmentId(id);

        model.addAttribute("department", updateDto);
        model.addAttribute("departmentId", id);
        model.addAttribute("teachers", teachers);

        return "admin/departments/edit-department";
    }

    @PostMapping("/edit/{id}")
    public String updateDepartment(@PathVariable Long id,
                                   @Valid @ModelAttribute("department") UpdateDepartmentDTO dto,
                                   BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            List<TeacherDropDownDTO> teachers = teacherService.getTeachersByDepartmentId(id);
            model.addAttribute("teachers", teachers);
            model.addAttribute("departmentId", id);
            return "admin/departments/edit-department";
        }

        this.departmentService.updateDepartment(dto, id);
        return "redirect:/admin/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        this.departmentService.deleteDepartment(id);
        return "redirect:/admin/departments";
    }
}
