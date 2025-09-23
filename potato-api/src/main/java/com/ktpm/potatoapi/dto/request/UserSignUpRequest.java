package com.ktpm.potatoapi.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignUpRequest {
    @NotBlank(message = "USER_EMAIL_BLANK")
    @Email(message = "USER_EMAIL_INVALID")
    String email;

    @NotBlank(message = "USER_PASSWORD_BLANK")
    @Size(min = 8, max = 20, message = "USER_PASSWORD_INVALID_SIZE")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "USER_PASSWORD_INVALID_PATTERN"
    )
    String password;

    @NotBlank(message = "USER_FULLNAME_BLANK")
    String fullName;
}
