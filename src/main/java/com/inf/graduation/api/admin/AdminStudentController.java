package com.inf.graduation.api.admin;

import com.inf.graduation.dto.student.CreateStudentDTO;
import com.inf.graduation.dto.student.StudentAdminViewDTO;
import com.inf.graduation.dto.student.UpdateStudentDTO;
import com.inf.graduation.dto.user.CreateUserDTO;
import com.inf.graduation.service.university.DepartmentService;
import com.inf.graduation.service.university.StudentService;
import com.inf.graduation.util.mapper.university.StudentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/students")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminStudentController {

    private final StudentService studentService;
    private final DepartmentService departmentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "admin/students/students";
    }

    @GetMapping("/create")
    public String showCreateStudentForm(Model model) {
        CreateStudentDTO createStudentDTO = new CreateStudentDTO();
        createStudentDTO.setUser(new CreateUserDTO());

        model.addAttribute("student", createStudentDTO);
        model.addAttribute("departments", departmentService.getDepartments());

        return "admin/students/create-student";
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") CreateStudentDTO createStudentDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getDepartments());
            return "admin/students/create-student";
        }
        this.studentService.createStudent(createStudentDTO);
        return "redirect:/admin/students";
    }

    @GetMapping("/edit-student/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        StudentAdminViewDTO adminView = studentService.getStudentById(id);

        UpdateStudentDTO updateStudentDTO = studentMapper.toUpdateStudentDTO(adminView);
        updateStudentDTO.getUser().setCurrentUsername(adminView.getUsername());

        model.addAttribute("student", updateStudentDTO);
        model.addAttribute("studentId", id);
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("user", updateStudentDTO.getUser());
        return "admin/students/edit-student";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") UpdateStudentDTO dto,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getDepartments());
            model.addAttribute("studentId", id);
            return "admin/students/edit-student";
        }

        this.studentService.updateStudent(dto,id);
        return "redirect:/admin/students";
    }


    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        this.studentService.deleteStudent(id);
        return "redirect:/admin/students";
    }
}
