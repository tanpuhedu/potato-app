package com.ktpm.potatoapi.dto.response;

import com.ktpm.potatoapi.enums.EntityStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;
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
    Map<String, String> openingHours;
    BigDecimal avgRating;
    int ratingCount;
    EntityStatus status;
    Set<String> cuisineTypes;
}
