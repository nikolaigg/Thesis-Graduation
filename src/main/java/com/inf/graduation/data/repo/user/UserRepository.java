package com.inf.graduation.data.repo.user;

import com.inf.graduation.data.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsername(String username);

    Optional<User> findByUserProfileId(Long profileId);

    boolean existsByUsername(String username);

}
