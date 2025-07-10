package com.inf.graduation.repository.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisGrade;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.thesis.ThesisGradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ThesisGradeRepositoryTest {

    @Autowired
    private ThesisGradeRepository thesisGradeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCountFinalGradeGreaterThanAndSupervisorId_Returns2() {
        Teacher supervisor = new Teacher();
        Thesis thesis1 = new Thesis();
        Thesis thesis2 = new Thesis();
        thesis1.setThesisSupervisor(supervisor);
        thesis2.setThesisSupervisor(supervisor);

        ThesisGrade grade1 = new ThesisGrade();
        grade1.setThesis(thesis1);
        grade1.setFinalGrade(BigDecimal.valueOf(5));

        ThesisGrade grade2 = new ThesisGrade();
        grade2.setThesis(thesis2);
        grade2.setFinalGrade(BigDecimal.valueOf(6));

        persistAll(supervisor,thesis1,thesis2,grade1,grade2);

        Long count = thesisGradeRepository.countByFinalGradeGreaterThanAndThesis_ThesisSupervisor_Id(BigDecimal.TWO, supervisor.getId());
        assertThat(count).isEqualTo(2);
    }

    private void persistAll(Object... entities) {
        for (Object entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
    }
}
