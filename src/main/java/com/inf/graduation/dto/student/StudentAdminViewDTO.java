package com.inf.graduation.dto.student;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentAdminViewDTO {

    private Long id;
    private String name;
    private String facultyNumber;
    private String departmentName;
    private Long departmentId;

    private String username;
}
