package com.inf.graduation.data.repo.thesis;

import com.inf.graduation.data.entity.thesis.ThesisDefenseSession;
import com.inf.graduation.data.entity.university.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ThesisDefenseSessionRepository extends JpaRepository<ThesisDefenseSession, Long> {

    @Query("select s.id from ThesisDefenseSession t join t.students s where t.defenseDate = :date")
    Set<Long> findStudentsScheduledOnDate(@Param("date") LocalDate date);

    @Query("select distinct s.id from ThesisDefenseSession t join t.students s")
    Set<Long> findAllScheduledStudentIds();

    List<ThesisDefenseSession> findByDefenseDateAndCommittee_Id(LocalDate defenseDate, Long committeeId);
    List<ThesisDefenseSession> findByDefenseDateAfterAndCommittee_Id(LocalDate date, Long teacherId);
    ThesisDefenseSession findByStudentsContaining(Student student);
}

