package com.inf.graduation.dto.thesisApplication;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateThesisApplicationDTO {


    @NotBlank
    private String topic;

    @NotBlank
    private String objective;

    @NotBlank
    private String tasks;

    @NotBlank
    private String usedTechnologies;
}
