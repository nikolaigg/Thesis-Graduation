package com.inf.graduation.dto.thesisApplication;

import com.inf.graduation.util.validation.annotation.NoActiveThesisApplication;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateThesisApplicationDTO {

    @NotBlank
    private String topic;

    @NotBlank
    private String objective;

    @NotBlank
    private String tasks;

    @NotBlank
    private String usedTechnologies;

    @NoActiveThesisApplication
    private Long studentId;

}
