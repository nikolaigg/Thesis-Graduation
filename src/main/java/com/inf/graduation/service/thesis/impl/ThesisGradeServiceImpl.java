package com.inf.graduation.service.thesis.impl;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisGrade;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.entity.university.TeacherGrade;
import com.inf.graduation.data.repo.university.TeacherGradeRepository;
import com.inf.graduation.data.repo.thesis.ThesisGradeRepository;
import com.inf.graduation.dto.thesis.GradedThesisDTO;
import com.inf.graduation.service.thesis.ThesisDefenseSessionService;
import com.inf.graduation.service.thesis.ThesisGradeService;
import com.inf.graduation.service.thesis.ThesisService;
import com.inf.graduation.util.mapper.thesis.ThesisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThesisGradeServiceImpl implements ThesisGradeService {

    private final ThesisGradeRepository thesisGradeRepository;
    private final ThesisDefenseSessionService thesisDefenseSessionService;
    private final TeacherGradeRepository teacherGradeRepository;
    private final ThesisService thesisService;
    private final ThesisMapper thesisMapper;



    @Override
    public void submitFinalGrade(Long thesisId) {
        List<TeacherGrade> grades = teacherGradeRepository.findByThesis_Id(thesisId);
        Thesis thesis = thesisService.getThesisById(thesisId);

        BigDecimal sum = BigDecimal.ZERO;
        for (TeacherGrade grade : grades) {
            sum = sum.add(BigDecimal.valueOf(grade.getGrade()));
        }

        BigDecimal finalGrade = sum.divide(BigDecimal.valueOf(grades.size()), 2, RoundingMode.HALF_UP);

        ThesisGrade thesisGrade = new ThesisGrade();
        thesisGrade.setThesis(thesis);
        thesisGrade.setFinalGrade(finalGrade);
        thesis.setStatus(ThesisStatus.GRADED);

        thesisGradeRepository.save(thesisGrade);
    }

    @Override
    public boolean allCommitteeMembersGraded(Long thesisId) {
        List<Teacher> committeeMembers = thesisDefenseSessionService.getCommitteeForThesis(thesisId);

        List<TeacherGrade> grades = teacherGradeRepository.findByThesis_Id(thesisId);


        Map<Long, Integer> teacherIdToGrade = grades.stream()
                .filter(g -> g.getGrade() != null)
                .collect(Collectors.toMap(
                        g -> g.getTeacher().getId(),
                        TeacherGrade::getGrade
                ));

        for (Teacher teacher : committeeMembers) {
            if (!teacherIdToGrade.containsKey(teacher.getId())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<GradedThesisDTO> getGradedTheses() {
            return this.thesisGradeRepository.findAll().stream()
                    .map(thesisMapper::toGradedThesisDTO)
                    .toList();
    }

    @Override
    public Long getStudentCountWithGradeAboveBySupervisor(Long teacherId) {
        return thesisGradeRepository.countByFinalGradeGreaterThanAndThesis_ThesisSupervisor_Id(BigDecimal.TWO, teacherId);
    }

    @Override
    public ThesisGrade getGradeByThesis(Long thesisId) {
        return this.thesisGradeRepository.findByThesisId(thesisId);
    }


}
