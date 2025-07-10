package com.inf.graduation.repository.thesis;

import com.inf.graduation.data.entity.thesis.ThesisDefenseSession;
import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.thesis.ThesisDefenseSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ThesisDefenseSessionRepositoryTest {

    @Autowired
    private ThesisDefenseSessionRepository thesisDefenseSessionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindStudentsOnScheduledDate_ReturnsSetOfStudentIdsOnCorrectDate() {
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();

        Student student4 = new Student();
        Student student5 = new Student();

        ThesisDefenseSession session = new ThesisDefenseSession();
        session.setStudents(Set.of(student1, student2, student3));
        session.setDefenseDate(LocalDate.of(2025,1,1));

        ThesisDefenseSession session2 = new ThesisDefenseSession();
        session2.setStudents(Set.of(student4,student5));
        session2.setDefenseDate(LocalDate.of(2026,2,2));

        persistAll(student1,student2,student3,student4,student5,session,session2);

        Set<Long> studentIdsOnDate = thesisDefenseSessionRepository.findStudentsScheduledOnDate(LocalDate.of(2025,1,1));
        Set<Long> expectedSet = Set.of(student1.getId(), student2.getId(), student3.getId());

        assertThat(studentIdsOnDate).isEqualTo(expectedSet);

    }

    @Test
    void testFindStudentsOnScheduledDate_ReturnsEmptySet() {
    assertThat(thesisDefenseSessionRepository.findStudentsScheduledOnDate(LocalDate.of(2025,1,1)).isEmpty()).isTrue();

    }

    @Test
    void testFindSessionByDateAndCommitteeId_ReturnsSession() {
        Teacher teacher = new Teacher();
        ThesisDefenseSession session = new ThesisDefenseSession();

        session.setCommittee(Set.of(teacher));
        session.setDefenseDate(LocalDate.of(2025,1,1));

        persistAll(teacher,session);

        List<ThesisDefenseSession> found = thesisDefenseSessionRepository.findByDefenseDateAndCommittee_Id(LocalDate.of(2025,1,1), teacher.getId());

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getId()).isEqualTo(session.getId());
        assertThat(found.get(0).getCommittee()).isEqualTo(Set.of(teacher));
        assertThat(found.get(0).getDefenseDate()).isEqualTo(LocalDate.of(2025,1,1));
    }

    @Test
    void testFindSessionByDateNotAndCommitteeId_ReturnsSession() {
        Teacher teacher = new Teacher();
        ThesisDefenseSession session1 = new ThesisDefenseSession();

        session1.setCommittee(Set.of(teacher));
        session1.setDefenseDate(LocalDate.of(2025,1,1));

        ThesisDefenseSession session2 = new ThesisDefenseSession();
        session2.setCommittee(Set.of(teacher));
        session2.setDefenseDate(LocalDate.of(2026,2,2));

        persistAll(teacher,session1,session2);

        List<ThesisDefenseSession> found = thesisDefenseSessionRepository.findByDefenseDateAfterAndCommittee_Id(LocalDate.of(2025,1,1), teacher.getId());

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getId()).isEqualTo(session2.getId());
        assertThat(found.get(0).getCommittee()).isEqualTo(Set.of(teacher));
        assertThat(found.get(0).getDefenseDate()).isNotEqualTo(LocalDate.of(2025,1,1));

    }
    @Test
    void testFindDefenseSessionByStudent_ReturnsCorrectSession() {
        Student student = new Student();
        ThesisDefenseSession session = new ThesisDefenseSession();
        session.setStudents(Set.of(student));

        persistAll(student, session);

        ThesisDefenseSession foundSession = thesisDefenseSessionRepository.findByStudentsContaining(student);

        assertThat(foundSession.getId()).isEqualTo(session.getId());
    }
    private void persistAll(Object... entities){
        for(Object entity : entities){
            entityManager.persist(entity);
        }
        entityManager.flush();
    }
}
