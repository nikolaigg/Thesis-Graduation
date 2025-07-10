package com.inf.graduation.dto.student;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentHomePageDTO {

    private String name;
    private String facultyNumber;
    private String departmentName;
    private boolean canUploadThesis;

}
