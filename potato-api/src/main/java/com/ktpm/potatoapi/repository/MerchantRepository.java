package com.ktpm.potatoapi.repository;

import com.ktpm.potatoapi.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    boolean existsByName(String name);

    @Query("""
        SELECT DISTINCT m
        FROM Merchant m
        LEFT JOIN m.cuisineType c
        WHERE m.status = 1
          AND (:keyword IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:minAvgRating IS NULL OR m.avgRating >= :minAvgRating)
          AND (:cuisineType IS NULL OR LOWER(c) = LOWER(:cuisineType))
        """)
    Page<Merchant> getByCriteria(String keyword, BigDecimal minAvgRating, String cuisineType, Pageable pageable);
}
