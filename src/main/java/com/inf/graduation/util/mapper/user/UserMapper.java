package com.inf.graduation.util.mapper.user;

import com.inf.graduation.data.entity.user.User;
import com.inf.graduation.dto.user.CreateUserDTO;
import com.inf.graduation.dto.user.UpdateUserDTO;
import com.inf.graduation.dto.user.UserAdminViewDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // DTO to Entity
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "enabled", constant = "true")
    User toUser(CreateUserDTO dto, @Context PasswordEncoder passwordEncoder);

    // Update
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    void updateUserFromDto(UpdateUserDTO dto, @MappingTarget User user, @Context PasswordEncoder passwordEncoder);
}
