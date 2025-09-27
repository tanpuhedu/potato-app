package com.ktpm.potatoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantUpdateRequest {
    @NotBlank(message = "MERCHANT_INTRO_BLANK")
    String introduction;

    @NotBlank(message = "MERCHANT_ADDRESS_BLANK")
    String address;

    @NotBlank(message = "MERCHANT_OPENING_HOURS_BLANK")
    Map<String, String> openingHours;

    @NotEmpty(message = "MERCHANT_CUISINE_TYPE_EMPTY")
    Set<String> cuisineTypes;
}
