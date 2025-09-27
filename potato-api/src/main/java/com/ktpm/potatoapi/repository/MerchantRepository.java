package com.ktpm.potatoapi.repository;

import com.ktpm.potatoapi.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMerchantAdmin_Email(String email);
}
