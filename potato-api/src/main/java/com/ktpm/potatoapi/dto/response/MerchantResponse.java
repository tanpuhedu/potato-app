package com.ktpm.potatoapi.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchantResponse {
    Long id;
    String name;
    String introduction;
    String address;
    String phone;
    Set<String> cuisineType;
    BigDecimal avgRating;
    int ratingCount;
    String openingHours;
    int status;
}
