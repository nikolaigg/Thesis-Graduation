package com.inf.graduation.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateDepartmentDTO {

    @NotBlank(message = "Department name required")
    @Size(min = 2, max = 50, message = "Department name must be between 2 and 50 characters")
    private String name;

    private Long headOfDepartmentId;
}
