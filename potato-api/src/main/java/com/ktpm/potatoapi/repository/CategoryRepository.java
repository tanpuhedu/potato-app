package com.ktpm.potatoapi.repository;

import com.ktpm.potatoapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
