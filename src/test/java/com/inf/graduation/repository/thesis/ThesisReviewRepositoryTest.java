package com.inf.graduation.repository.thesis;

import com.inf.graduation.data.entity.thesis.Thesis;
import com.inf.graduation.data.entity.thesis.ThesisReview;
import com.inf.graduation.data.repo.thesis.ThesisReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ThesisReviewRepositoryTest {

    @Autowired
    private ThesisReviewRepository thesisReviewRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByThesisId_ReturnsCorrectReview() {
        Thesis thesis = new Thesis();
        ThesisReview review = new ThesisReview();

        thesis.setReview(review);
        review.setThesisReviewed(thesis);

        entityManager.persist(thesis);
        entityManager.persist(review);
        entityManager.flush();

        ThesisReview reviewFound = thesisReviewRepository.findByThesisReviewed_Id(thesis.getReview().getId());

        assertThat(reviewFound.getId()).isEqualTo(review.getId());

    }
}
