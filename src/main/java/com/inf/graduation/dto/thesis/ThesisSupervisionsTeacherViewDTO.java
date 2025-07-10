package com.inf.graduation.dto.thesis;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisSupervisionsTeacherViewDTO {

    private String studentName;
    private String studentFacultyNumber;
    private String title;
    private String description;
    private LocalDate uploadDate;
    private String reviewAssessment;
    private String status;
}
