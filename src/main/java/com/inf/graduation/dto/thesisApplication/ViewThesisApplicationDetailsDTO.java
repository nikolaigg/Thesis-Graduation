package com.inf.graduation.dto.thesisApplication;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ViewThesisApplicationDetailsDTO {

    private String topic;
    private String objective;
    private String tasks;
    private String usedTechnologies;

    private String studentName;
    private String teacherName;
    private String status;
}
