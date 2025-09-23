package com.ktpm.potatoapi.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLogInResponse {
    String fullName;
    String email;
    String token;
}
