package com.inf.graduation.data.repo.thesis;

import com.inf.graduation.data.entity.thesis.ThesisReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThesisReviewRepository extends JpaRepository<ThesisReview, Long> {

    ThesisReview findByThesisReviewed_Id(Long thesisId);
}
