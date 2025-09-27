package com.ktpm.potatoapi.dto.response;

import com.ktpm.potatoapi.enums.RegistrationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantRegistrationResponse {
    Long id;
    String merchantName;
    String address;
    Set<String> cuisineType;
    String fullName;
    String email;
    RegistrationStatus registrationStatus;
}
