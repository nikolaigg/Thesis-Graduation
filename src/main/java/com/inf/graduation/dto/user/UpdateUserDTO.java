package com.inf.graduation.dto.user;

import com.inf.graduation.util.validation.annotation.ValidUsernameUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ValidUsernameUpdate
public class UpdateUserDTO {

    @NotBlank(message = "Username required")
    @Size(min = 3, max = 50)
    private String username;

    private String currentUsername;

    @Size(min = 6, max = 60)
    private String password;

}
