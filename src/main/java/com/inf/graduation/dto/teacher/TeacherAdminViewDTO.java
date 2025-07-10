package com.inf.graduation.dto.teacher;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherAdminViewDTO {

    private Long id;
    private String name;

    private String departmentName;
    private Long departmentId;

    private String username;

}
