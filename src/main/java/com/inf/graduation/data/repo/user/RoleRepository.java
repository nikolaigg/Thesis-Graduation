package com.inf.graduation.data.repo.user;

import com.inf.graduation.data.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
}
