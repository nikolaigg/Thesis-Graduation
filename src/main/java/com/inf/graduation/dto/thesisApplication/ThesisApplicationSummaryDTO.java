package com.inf.graduation.dto.thesisApplication;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisApplicationSummaryDTO {

    private Long id;

    private String topic;

    private Long studentId;
    private String studentName;

    private Long teacherId;
    private String teacherName;
}
