package com.inf.graduation.dto.thesis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadThesisDTO {

    @NotBlank
    @Size(min = 5, max = 20)
    private String title;

    @NotBlank
    @Size(min = 10, max = 100)
    private String description;
}
