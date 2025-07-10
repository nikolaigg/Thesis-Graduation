package com.inf.graduation.util.mapper.thesis;

import com.inf.graduation.data.entity.thesis.ThesisReview;
import com.inf.graduation.dto.thesisReview.ReviewGradingDTO;
import com.inf.graduation.dto.thesisReview.WriteThesisReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ThesisReviewMapper {

    @Mapping(target = "assessment", source = "status")
    ReviewGradingDTO toReviewGradingDTO(ThesisReview dto);

    void updateThesisReviewFromDto(WriteThesisReviewDTO dto, @MappingTarget ThesisReview thesisReview);
}
