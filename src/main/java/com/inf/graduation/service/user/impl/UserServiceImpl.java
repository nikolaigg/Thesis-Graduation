package com.inf.graduation.service.user.impl;

import com.inf.graduation.data.entity.user.Role;
import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.data.repo.user.RoleRepository;
import com.inf.graduation.data.repo.user.UserRepository;
import com.inf.graduation.dto.user.CreateUserDTO;
import com.inf.graduation.dto.user.UpdateUserDTO;
import com.inf.graduation.service.user.UserService;
import com.inf.graduation.util.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return this.userRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User createUser(CreateUserDTO dto, String role) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username " + dto.getUsername() + " already exists");
        }

        Role assignRole = roleRepository.findByAuthority(role)
                .orElseThrow(() -> new IllegalArgumentException("Role " + role + " does not exist"));

        User user = userMapper.toUser(dto, passwordEncoder);
        user.setRoles(Set.of(assignRole));


        return user;
    }

    @Override
    public User updateUser(UpdateUserDTO dto, Long id) {
        User user = userRepository.findByUserProfileId(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userMapper.updateUserFromDto(dto, user, passwordEncoder);
        return user;
    }


}
