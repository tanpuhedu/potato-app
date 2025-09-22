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
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "EMAIL", unique = true, nullable = false)
    String email;

    @Column(name = "FULLNAME")
    String fullName;

    @Column(name = "PASSWORD", nullable = false)
    String password;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    EntityStatus status;

    @PrePersist
    protected void onCreate() {
        this.status = EntityStatus.ACTIVE;
        this.role = Role.CUSTOMER;
    }

}
