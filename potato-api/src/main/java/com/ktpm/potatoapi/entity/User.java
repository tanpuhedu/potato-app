package com.ktpm.potatoapi.entity;

import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EntityStatus status;

    @PrePersist
    protected void onCreate() {
        this.status = EntityStatus.ACTIVE;
        this.role = Role.CUSTOMER;
    }
}
