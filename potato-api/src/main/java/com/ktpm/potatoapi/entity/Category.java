package com.ktpm.potatoapi.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Category entity for managing food categories.
 * Represents food categories in the potato system.
 * Stores a unique name for each food category.
 * Helps organize and classify food category, making it easier to extend service logic and scale the platform.
 * Why created Food Category:
 * - To separate food classification from the Dish entity.
 * - Examples: Burger, Pizza, Noodle,....
 * @author Hieu
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String name;
}
