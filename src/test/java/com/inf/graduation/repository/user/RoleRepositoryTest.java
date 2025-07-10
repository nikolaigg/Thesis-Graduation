package com.inf.graduation.repository.user;

import com.inf.graduation.data.entity.user.Role;
import com.inf.graduation.data.repo.user.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByAuthority_ReturnsCorrectAuthority() {
        Role role = new Role();
        role.setAuthority("ROLE_TEST");

        entityManager.persistAndFlush(role);

        Optional<Role> optionalRole = roleRepository.findByAuthority("ROLE_TEST");

        assertThat(optionalRole.isPresent()).isTrue();
        assertThat(optionalRole.get().getAuthority()).isEqualTo("ROLE_TEST");

    }

    @Test
    void testFindByAuthority_ReturnsEmptyWhenNotFound() {
        Optional<Role> optionalRole = roleRepository.findByAuthority("ROLE_NOT_FOUND");

        assertThat(optionalRole.isPresent()).isFalse();

    }
}
