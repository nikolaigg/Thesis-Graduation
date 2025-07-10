package com.inf.graduation.dto.thesis;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradedThesisDTO {

    private String title;
    private String studentName;
    private String studentFacultyNumber;
    private String supervisorName;
    private BigDecimal grade;
}
