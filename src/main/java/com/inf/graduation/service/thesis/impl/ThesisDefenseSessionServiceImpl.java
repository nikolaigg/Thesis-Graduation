package com.inf.graduation.service.thesis.impl;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisDefenseSession;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.university.StudentRepository;
import com.inf.graduation.data.repo.university.TeacherRepository;
import com.inf.graduation.data.repo.thesis.ThesisDefenseSessionRepository;
import com.inf.graduation.data.repo.thesis.ThesisRepository;
import com.inf.graduation.dto.student.StudentDropdownDTO;
import com.inf.graduation.dto.teacher.TeacherDropDownDTO;
import com.inf.graduation.dto.thesis.ThesisSummaryDTO;
import com.inf.graduation.dto.thesisDefense.DefenseSessionSetupDto;
import com.inf.graduation.dto.thesisDefense.DefenseSessionStudentsDto;
import com.inf.graduation.dto.thesisDefense.DefenseSummaryDTO;
import com.inf.graduation.service.thesis.ThesisDefenseSessionService;
import com.inf.graduation.service.university.StudentService;
import com.inf.graduation.service.university.TeacherGradeService;
import com.inf.graduation.service.university.TeacherService;
import com.inf.graduation.util.mapper.thesis.ThesisDefenseSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ThesisDefenseSessionServiceImpl implements ThesisDefenseSessionService {

    private final ThesisDefenseSessionRepository thesisDefenseSessionRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ThesisRepository thesisRepository;
    private final ThesisDefenseSessionMapper thesisDefenseSessionMapper;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final TeacherGradeService teacherGradeService;

    @Override
    public ThesisDefenseSession createThesisDefenseSession(DefenseSessionStudentsDto dto) {
        Set<Student> students = studentRepository.findByIdIn(dto.getStudentIds());
        Set<Teacher> teachers = teacherRepository.findByIdIn(dto.getCommitteeIds());

        ThesisDefenseSession thesisDefenseSession = new ThesisDefenseSession();
        thesisDefenseSession.setDefenseDate(dto.getDefenseDate());
        thesisDefenseSession.setCommittee(teachers);
        thesisDefenseSession.setStudents(students);

        for(Student student : students) {
            student.getThesis().setStatus(ThesisStatus.SCHEDULED_FOR_DEFENSE);
        }
        return thesisDefenseSessionRepository.save(thesisDefenseSession);

    }

    @Override
    public List<DefenseSummaryDTO> getDefensesByDateAndTeacherId(LocalDate date, Long teacherId) {
        List<ThesisDefenseSession> defenses = thesisDefenseSessionRepository
                .findByDefenseDateAndCommittee_Id(date, teacherId);

        return getDefenseSummaryDTOS(defenses,teacherId);
    }

    @Override
    public List<DefenseSummaryDTO> getDefensesNotOnDateAndTeacherId(LocalDate date, Long teacherId) {
        List<ThesisDefenseSession> defenses = thesisDefenseSessionRepository
                .findByDefenseDateAfterAndCommittee_Id(date, teacherId);

        return getDefenseSummaryDTOS(defenses,teacherId);
    }

    @Override
    public List<Teacher> getCommitteeForThesis(Long thesisId) {
        Thesis thesis = thesisRepository.findById(thesisId)
                .orElseThrow(() -> new RuntimeException("Thesis not found"));

        Student student = thesis.getStudent();

        ThesisDefenseSession defense = thesisDefenseSessionRepository.findByStudentsContaining(student);

        return new ArrayList<>(defense.getCommittee());

    }

    @Override
    public DefenseSessionStudentsDto mapToDefenseSessionStudentsDto(DefenseSessionSetupDto dto) {
        return this.thesisDefenseSessionMapper.toDefenseSessionStudentsDto(dto);
    }

    @Override
    public void setCommitteeNames(DefenseSessionSetupDto defense) {
        List<TeacherDropDownDTO> committeeMembers = teacherService.getTeachersByIds(defense.getCommitteeIds());

        List<String> committeeNames = committeeMembers.stream()
                .map(TeacherDropDownDTO::getName)
                .toList();

        defense.setCommitteeNames(committeeNames);
    }

    @Override
    public List<StudentDropdownDTO> getEligibleStudentsForDefense(DefenseSessionSetupDto defense) {
        List<TeacherDropDownDTO> committeeMembers = teacherService.getTeachersByIds(defense.getCommitteeIds());

        List<Long> committeeIds = committeeMembers.stream()
                .map(TeacherDropDownDTO::getId)
                .toList();

        return studentService.getEligibleStudentsForDefense(committeeIds, defense.getDefenseDate());
    }

    @Override
    public void setStudentNames(DefenseSessionStudentsDto defense) {
        List<StudentDropdownDTO> students = studentService.getStudentsByIds(defense.getStudentIds());

        List<String> studentNames = students.stream()
                .map(StudentDropdownDTO::getName)
                .toList();

        defense.setStudentNames(studentNames);
    }


    private List<DefenseSummaryDTO> getDefenseSummaryDTOS(List<ThesisDefenseSession> defenses, Long teacherId) {
        List<DefenseSummaryDTO> result = new ArrayList<>();

        for (ThesisDefenseSession defense : defenses) {
            DefenseSummaryDTO defenseDto = new DefenseSummaryDTO();
            defenseDto.setId(defense.getId());
            defenseDto.setDefenseDate(defense.getDefenseDate());

            List<ThesisSummaryDTO> theses = getThesisSummaryDTOS(defense, teacherId);
            defenseDto.setTheses(theses);
            result.add(defenseDto);
        }
        return result;
    }

    private List<ThesisSummaryDTO> getThesisSummaryDTOS(ThesisDefenseSession defense, Long teacherId) {
        List<ThesisSummaryDTO> theses = new ArrayList<>();
        for (Student student : defense.getStudents()) {
            if (student.getThesis() != null) {
                ThesisSummaryDTO thesisDto = new ThesisSummaryDTO();
                thesisDto.setId(student.getThesis().getId());
                thesisDto.setTitle(student.getThesis().getTitle());
                thesisDto.setStudentName(student.getName());

                Integer grade = teacherGradeService.getGradeForThesisByTeacher(student.getThesis().getId(), teacherId);
                thesisDto.setGrade(grade);

                theses.add(thesisDto);
            }
        }
        return theses;
    }

}
