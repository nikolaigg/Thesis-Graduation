package com.inf.graduation.dto.department;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentAdminViewDTO {

    private Long id;
    private String name;

    private Long headOfDepartmentId;
    private String headOfDepartmentName;

}
