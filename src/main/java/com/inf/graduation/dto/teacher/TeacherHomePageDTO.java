package com.inf.graduation.dto.teacher;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherHomePageDTO {

    private String name;
    private String departmentName;

    private boolean headOfDepartment;
}
