package com.inf.graduation.dto.thesis;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisStudentViewDTO {

    private String title;
    private String description;
    private LocalDate uploadDate;
    private String status;
    private BigDecimal grade;
}
