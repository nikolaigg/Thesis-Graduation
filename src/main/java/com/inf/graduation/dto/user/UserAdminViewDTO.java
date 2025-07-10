package com.inf.graduation.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAdminViewDTO {

    private Long id;
    private String username;
    private String password;
    private Long profileId;
}
