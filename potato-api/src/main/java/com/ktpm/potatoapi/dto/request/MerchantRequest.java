package com.ktpm.potatoapi.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantRequest {
    String name;
    String introduction;
    String address;
    String phone;
    Set<String> cuisineType;
    String openingHours;
}
