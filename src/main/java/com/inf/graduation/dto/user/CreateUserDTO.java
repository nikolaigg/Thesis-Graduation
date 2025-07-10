package com.inf.graduation.dto.user;

import com.inf.graduation.util.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserDTO {

    @NotBlank(message = "Username required")
    @Size(min = 3, max = 50)
    @UniqueUsername
    private String username;

    @NotBlank(message = "Password required")
    @Size(min = 6, max = 60)
    private String password;

    private Long profileId;
}
