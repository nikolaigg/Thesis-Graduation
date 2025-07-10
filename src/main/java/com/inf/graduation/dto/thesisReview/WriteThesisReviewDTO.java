package com.inf.graduation.dto.thesisReview;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WriteThesisReviewDTO {

    private String review;
    private String status;
    private Long thesisId;
}
