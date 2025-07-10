package com.inf.graduation.service.thesis;

import com.inf.graduation.dto.thesisReview.ReviewGradingDTO;
import com.inf.graduation.dto.thesisReview.WriteThesisReviewDTO;

public interface ThesisReviewService {

    void writeThesisReview(WriteThesisReviewDTO dto);
    ReviewGradingDTO getReviewByThesisId(Long thesisId);
}
