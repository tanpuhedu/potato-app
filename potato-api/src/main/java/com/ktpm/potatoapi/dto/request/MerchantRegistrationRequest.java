package com.ktpm.potatoapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantRegistrationRequest {
    @NotBlank(message = "REGISTERED_MERCHANT_FULLNAME_BLANK")
    String fullName;

    @NotBlank(message = "REGISTERED_MERCHANT_EMAIL_BLANK")
    @Email(message = "REGISTERED_MERCHANT_EMAIL_INVALID")
    String email;

    @NotBlank(message = "REGISTERED_MERCHANT_NAME_BLANK")
    String merchantName;

    @NotBlank(message = "REGISTERED_MERCHANT_ADDRESS_BLANK")
    String address;

    @NotEmpty(message = "REGISTERED_MERCHANT_CUISINE_TYPE_EMPTY")
    Set<String> cuisineTypes;
}
