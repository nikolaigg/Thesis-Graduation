package com.inf.graduation.service.thesis.impl;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisReview;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.repo.thesis.ThesisRepository;
import com.inf.graduation.data.repo.thesis.ThesisReviewRepository;
import com.inf.graduation.dto.thesisReview.ReviewGradingDTO;
import com.inf.graduation.dto.thesisReview.WriteThesisReviewDTO;
import com.inf.graduation.service.thesis.ThesisReviewService;
import com.inf.graduation.util.mapper.thesis.ThesisReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThesisReviewServiceImpl implements ThesisReviewService {

    private final ThesisReviewRepository thesisReviewRepository;
    private final ThesisReviewMapper thesisReviewMapper;
    private final ThesisRepository thesisRepository;

    @Override
    public void writeThesisReview(WriteThesisReviewDTO dto) {
        Thesis thesis = thesisRepository.findById(dto.getThesisId())
                .orElseThrow(() -> new RuntimeException("Thesis Not Found"));

        ThesisReview thesisReview = thesis.getReview();
        thesisReviewMapper.updateThesisReviewFromDto(dto, thesisReview);
        thesis.setStatus(ThesisStatus.REVIEWED);

        thesisReviewRepository.save(thesisReview);
    }

    @Override
    public ReviewGradingDTO getReviewByThesisId(Long thesisId) {
        ThesisReview review = thesisReviewRepository.findByThesisReviewed_Id(thesisId);
        return thesisReviewMapper.toReviewGradingDTO(review);
    }
}
