package com.ktpm.potatoapi.entity;

import com.ktpm.potatoapi.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisteredMerchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String merchantName;

    @Column(nullable = false)
    String address;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    Set<String> cuisineTypes;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RegistrationStatus registrationStatus;
}
