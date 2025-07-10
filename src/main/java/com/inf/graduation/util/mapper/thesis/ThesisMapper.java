package com.inf.graduation.util.mapper.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisGrade;
import com.inf.graduation.dto.thesis.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThesisMapper {

    // DTO to Entity
    Thesis toThesis(UploadThesisDTO dto);

    // Entity to DTO
    @Mapping(target = "status", source = "review.status")
    @Mapping(target = "grade", ignore = true)
    ThesisStudentViewDTO toThesisStudentViewDTO(Thesis thesis);

    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "studentFacultyNumber", source = "student.facultyNumber")
    @Mapping(target = "reviewAssessment", source = "review.status")
    ThesisSupervisionsTeacherViewDTO toThesisSupervisionsTeacherViewDTO(Thesis thesis);

    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "studentFacultyNumber", source = "student.facultyNumber")
    @Mapping(target = "supervisorName", source = "thesisSupervisor.name")
    PendingThesisDTO toPendingThesisDTO(Thesis thesis);

    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "studentFacultyNumber", source = "student.facultyNumber")
    @Mapping(target = "supervisorName", source = "thesisSupervisor.name")
    ThesisGradingDTO toThesisGradingDTO(Thesis thesis);


    @Mapping(target = "title", source = "thesis.title")
    @Mapping(target = "studentName", source = "thesis.student.name")
    @Mapping(target = "studentFacultyNumber", source = "thesis.student.facultyNumber")
    @Mapping(target = "supervisorName", source = "thesis.thesisSupervisor.name")
    @Mapping(target = "grade", expression = "java(thesisGrade.getFinalGrade() != null ? thesisGrade.getFinalGrade() : null)")
    GradedThesisDTO toGradedThesisDTO(ThesisGrade thesisGrade);
}
