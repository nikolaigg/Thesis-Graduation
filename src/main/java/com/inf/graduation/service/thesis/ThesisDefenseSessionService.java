package com.inf.graduation.service.thesis;

import com.inf.graduation.data.entity.thesis.ThesisDefenseSession;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.dto.student.StudentDropdownDTO;
import com.inf.graduation.dto.thesisDefense.DefenseSessionSetupDto;
import com.inf.graduation.dto.thesisDefense.DefenseSessionStudentsDto;
import com.inf.graduation.dto.thesisDefense.DefenseSummaryDTO;

import java.time.LocalDate;
import java.util.List;

public interface ThesisDefenseSessionService {

    ThesisDefenseSession createThesisDefenseSession(DefenseSessionStudentsDto dto);
    List<DefenseSummaryDTO> getDefensesByDateAndTeacherId(LocalDate date, Long teacherId);
    List<DefenseSummaryDTO> getDefensesNotOnDateAndTeacherId(LocalDate date, Long teacherId);
    List<Teacher> getCommitteeForThesis(Long thesisId);
    DefenseSessionStudentsDto mapToDefenseSessionStudentsDto(DefenseSessionSetupDto dto);
    void setCommitteeNames(DefenseSessionSetupDto defense);
    List<StudentDropdownDTO> getEligibleStudentsForDefense(DefenseSessionSetupDto defense);
    void setStudentNames(DefenseSessionStudentsDto defense);
}
