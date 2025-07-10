package com.inf.graduation.repository.university;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.university.TeacherGrade;
import com.inf.graduation.data.repo.university.TeacherGradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TeacherGradeRepositoryTest {

    @Autowired
    private TeacherGradeRepository teacherGradeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindGradesByThesisId_ReturnsCorrectGrades() {
        Thesis thesis = new Thesis();

        TeacherGrade grade1 = new TeacherGrade();
        grade1.setThesis(thesis);

        TeacherGrade grade2 = new TeacherGrade();
        grade2.setThesis(thesis);

        entityManager.persist(thesis);
        entityManager.persist(grade1);
        entityManager.persist(grade2);
        entityManager.flush();

        List<TeacherGrade> grades = teacherGradeRepository.findByThesis_Id(thesis.getId());

        assertThat(grades.size()).isEqualTo(2);
        assertThat(grades.get(0).getThesis().getId()).isEqualTo(thesis.getId());
        assertThat(grades.get(1).getThesis().getId()).isEqualTo(thesis.getId());
    }
}
