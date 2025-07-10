package com.inf.graduation.dto.thesis;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisSummaryDTO {

    private Long id;
    private String title;
    private String studentName;
    private Integer grade;
}
