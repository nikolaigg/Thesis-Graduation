package com.inf.graduation.service.user;

import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.dto.user.CreateUserDTO;
import com.inf.graduation.dto.user.UpdateUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(CreateUserDTO dto, String role);
    User updateUser(UpdateUserDTO dto, Long id);
}
