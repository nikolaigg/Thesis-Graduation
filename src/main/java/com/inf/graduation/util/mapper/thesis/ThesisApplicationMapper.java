package com.inf.graduation.util.mapper.thesis;

import com.inf.graduation.data.entity.thesis.ThesisApplication;
import com.inf.graduation.dto.thesisApplication.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ThesisApplicationMapper {


    // Entity to DTO
    @Mapping(target = "studentName", source ="student.name")
    MyThesisApplicationDTO toMyThesisApplicationDTO(ThesisApplication thesisApplication);

    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "teacherName", source = "thesisSupervisor.name")
    ThesisApplicationSummaryDTO toThesisApplicationSummaryDTO(ThesisApplication thesisApplication);

    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "teacherName", source = "thesisSupervisor.name")
    ViewThesisApplicationDetailsDTO toViewThesisApplicationDetailsDTO(ThesisApplication thesisApplication);

    UpdateThesisApplicationDTO toUpdateThesisApplicationDTO(ThesisApplication thesisApplication);

    @Mapping(target = "teacherName", source = "thesisSupervisor.name")
    StudentThesisApplicationDetailsDTO toStudentThesisApplicationDetailsDTO(ThesisApplication thesisApplication);

    ThesisApplicationGradingViewDTO toThesisApplicationGradingViewDTO(ThesisApplication thesisApplication);

    // DTO to Entity
    ThesisApplication toThesisApplication(CreateThesisApplicationDTO dto);

    // Update
    void updateThesisApplication(UpdateThesisApplicationDTO dto, @MappingTarget ThesisApplication thesisApplication);
}
