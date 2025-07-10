package com.inf.graduation.dto.teacher;

import com.inf.graduation.dto.user.CreateUserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateTeacherDTO {

    @NotBlank
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Department must be selected")
    private Long departmentId;

    @Valid
    private CreateUserDTO user;
}
