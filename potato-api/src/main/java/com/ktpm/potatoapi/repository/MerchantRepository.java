package com.ktpm.potatoapi.repository;

import com.ktpm.potatoapi.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    boolean existsByName(String name);
}
