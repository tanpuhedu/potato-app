package com.ktpm.potatoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLogInRequest {
    @NotBlank(message = "USER_EMAIL_BLANK")
    String email;

    @NotBlank(message = "USER_PASSWORD_BLANK")
    String password;
}