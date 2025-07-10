package com.inf.graduation.service.thesis;

import com.inf.graduation.data.entity.thesis.ThesisGrade;
import com.inf.graduation.dto.thesis.GradedThesisDTO;

import java.util.List;

public interface ThesisGradeService {

    void submitFinalGrade(Long thesisId);
    boolean allCommitteeMembersGraded(Long thesisId);
    List<GradedThesisDTO> getGradedTheses();
    Long getStudentCountWithGradeAboveBySupervisor(Long teacherId);
    ThesisGrade getGradeByThesis(Long thesisId);
}
