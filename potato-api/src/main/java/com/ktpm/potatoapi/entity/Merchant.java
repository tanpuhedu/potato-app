package com.ktpm.potatoapi.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String introduction;
    String address;
    String phone;

    @Column(precision = 2, scale = 1)
    @Builder.Default
    BigDecimal avgRating = BigDecimal.ZERO;

    @Builder.Default
    int ratingCount = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    String openingHours;

    @Builder.Default
    int status = 0;
}
