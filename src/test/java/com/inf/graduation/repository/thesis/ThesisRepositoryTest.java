package com.inf.graduation.repository.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisReview;
import com.inf.graduation.data.entity.thesis.enums.ThesisReviewAssessment;
import com.inf.graduation.data.entity.thesis.enums.ThesisStatus;
import com.inf.graduation.data.entity.university.Department;
import com.inf.graduation.data.entity.university.Teacher;
import com.inf.graduation.data.repo.thesis.ThesisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ThesisRepositoryTest {

    @Autowired
    private ThesisRepository thesisRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindThesesBySupervisorId_ReturnsCorrectTheses() {
        Teacher supervisor = new Teacher();
        Thesis thesis1 = new Thesis();
        thesis1.setThesisSupervisor(supervisor);

        Thesis thesis2 = new Thesis();
        thesis2.setThesisSupervisor(supervisor);

        persistAll(supervisor, thesis1, thesis2);

        List<Thesis> theses = thesisRepository.findByThesisSupervisorId(supervisor.getId());

        assertThat(theses.size()).isEqualTo(2);
        assertThat(theses.get(0).getThesisSupervisor().getId()).isEqualTo(supervisor.getId());
        assertThat(theses.get(1).getThesisSupervisor().getId()).isEqualTo(supervisor.getId());
    }

    @Test
    void testFindThesesByReviewStatusAndBySupervisorDepartmentAndSupervisorNot_ReturnsCorrectTheses() {
        Department department = new Department();

        Teacher supervisorNot = new Teacher();
        supervisorNot.setDepartment(department);
        Teacher supervisor1 = new Teacher();
        supervisor1.setDepartment(department);
        Teacher supervisor2 = new Teacher();
        supervisor2.setDepartment(department);

        Thesis thesis1 = new Thesis();
        ThesisReview review1 = new ThesisReview();
        review1.setThesisReviewed(thesis1);
        review1.setStatus(ThesisReviewAssessment.PENDING);
        thesis1.setThesisSupervisor(supervisor1);
        thesis1.setReview(review1);


        Thesis thesis2 = new Thesis();
        ThesisReview review2 = new ThesisReview();
        review2.setThesisReviewed(thesis2);
        review2.setStatus(ThesisReviewAssessment.PENDING);
        thesis2.setThesisSupervisor(supervisor2);
        thesis2.setReview(review2);

        persistAll(department, supervisor1, supervisor2, supervisorNot, thesis1, review1, thesis2, review2);

        List<Thesis> theses = thesisRepository.findByReview_StatusAndThesisSupervisor_DepartmentAndThesisSupervisorNot(ThesisReviewAssessment.PENDING, department, supervisorNot);

        assertThat(theses.size()).isEqualTo(2);

        assertThat(theses.get(0).getReview().getStatus()).isEqualTo(ThesisReviewAssessment.PENDING);
        assertThat(theses.get(1).getReview().getStatus()).isEqualTo(ThesisReviewAssessment.PENDING);

        assertThat(theses.get(0).getThesisSupervisor().getDepartment().getId()).isEqualTo(department.getId());
        assertThat(theses.get(1).getThesisSupervisor().getDepartment().getId()).isEqualTo(department.getId());

        assertThat(theses.get(0).getThesisSupervisor().getId()).isNotEqualTo(supervisorNot.getId());
        assertThat(theses.get(1).getThesisSupervisor().getId()).isNotEqualTo(supervisorNot.getId());

    }
    @Test
    void testFindThesesByStatusAndSupervisorDepartment_ReturnsCorrectTheses() {
        Department department = new Department();
        Teacher supervisor = new Teacher();
        supervisor.setDepartment(department);

        Thesis thesis1 = new Thesis();
        thesis1.setThesisSupervisor(supervisor);
        thesis1.setStatus(ThesisStatus.GRADED);

        Thesis thesis2 = new Thesis();
        thesis2.setThesisSupervisor(supervisor);
        thesis2.setStatus(ThesisStatus.GRADED);

        persistAll(department, supervisor, thesis1, thesis2);

        List<Thesis> theses = thesisRepository.findByStatusAndThesisSupervisor_Department(ThesisStatus.GRADED, department);

        assertThat(theses.size()).isEqualTo(2);

        assertThat(theses.get(0).getThesisSupervisor().getDepartment().getId()).isEqualTo(department.getId());
        assertThat(theses.get(1).getThesisSupervisor().getDepartment().getId()).isEqualTo(department.getId());

        assertThat(theses.get(0).getStatus()).isEqualTo(ThesisStatus.GRADED);
        assertThat(theses.get(1).getStatus()).isEqualTo(ThesisStatus.GRADED);

    }

    private void persistAll(Object... entities) {
        for (Object entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
    }
}
