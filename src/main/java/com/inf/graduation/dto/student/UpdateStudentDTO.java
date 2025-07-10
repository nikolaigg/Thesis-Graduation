package com.inf.graduation.dto.student;

import com.inf.graduation.dto.user.UpdateUserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateStudentDTO {

    @NotBlank
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[F].*", message = "Faculty number must start with F")
    @Size(min = 2, max = 50, message = "Faculty number must be between 2 and 50 characters")
    private String facultyNumber;

    @NotNull(message = "Department must be selected")
    private Long departmentId;

    @Valid
    private UpdateUserDTO user;
}
