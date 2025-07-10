package com.inf.graduation.dto.thesis;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisGradingDTO {

    private Long id;
    private String title;
    private String description;
    private String studentName;
    private String studentFacultyNumber;
    private String supervisorName;
}
