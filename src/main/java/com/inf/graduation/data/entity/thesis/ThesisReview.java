package com.inf.graduation.data.entity.thesis;

import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    @OneToOne
    @JoinColumn(name = "thesis_id", nullable = false)
    private Thesis thesisReviewed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThesisReviewAssessment status = ThesisReviewAssessment.PENDING;
}
