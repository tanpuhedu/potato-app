package com.ktpm.potatoapi.repository;

import com.ktpm.potatoapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    // Thành code
    /*
        boolean existsByUsername(String username);

     */
}
