package com.inf.graduation.dto.thesis;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PendingThesisDTO {

    private Long id;
    private String title;
    private String description;
    private String studentName;
    private String studentFacultyNumber;
    private String supervisorName;
    private LocalDate uploadDate;
}
