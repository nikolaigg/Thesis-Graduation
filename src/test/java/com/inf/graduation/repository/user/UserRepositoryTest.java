package com.inf.graduation.repository.user;

import com.inf.graduation.data.entity.university.Student;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.repo.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByUsername_ReturnsCorrectUser() {
        User user = new User();
        user.setUsername("testUsername");
        entityManager.persistAndFlush(user);

        Optional<User> userOptional = userRepository.findByUsername("testUsername");

        assertThat(userOptional.isPresent()).isTrue();
        assertThat(userOptional.get().getUsername()).isEqualTo("testUsername");

    }

    @Test
    void testFindByUsername_ReturnsEmptyWhenUserNotFound() {
        Optional<User> userOptional = userRepository.findByUsername("testUsername");

        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    void testFindByUserProfileId_ReturnsCorrectUser() {
        Student student = new Student();
        entityManager.persist(student);

        User user = new User();
        user.setUserProfile(student);

        entityManager.persist(user);
        entityManager.flush();

        Optional<User> optionalUser = userRepository.findByUserProfileId(student.getId());

        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get().getUserProfile()).isEqualTo(student);

    }

    @Test
    void testFindByUserProfileId_ReturnsEmptyWhenUserNotFound() {
        Student student = new Student();
        entityManager.persistAndFlush(student);

        Optional<User> optionalUser = userRepository.findByUserProfileId(student.getId());

        assertThat(optionalUser.isPresent()).isFalse();
    }

    @Test
    void testExistsByUsername_ReturnsTrueWhenUsernameExists() {
        User user = new User();
        user.setUsername("testUsername");
        entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByUsername("testUsername");

        assertThat(exists).isTrue();
    }

    @Test
    void testExistsByUsername_ReturnsFalseWhenUsernameDoesNotExist() {
        boolean exists = userRepository.existsByUsername("testUsername");
        assertThat(exists).isFalse();
    }
}
