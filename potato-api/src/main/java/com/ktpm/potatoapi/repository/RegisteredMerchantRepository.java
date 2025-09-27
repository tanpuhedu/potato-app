package com.ktpm.potatoapi.repository;

import com.ktpm.potatoapi.entity.RegisteredMerchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredMerchantRepository extends JpaRepository<RegisteredMerchant, Long> {
}
