package com.example.wellness_javaproj.repository;

import com.example.wellness_javaproj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles database operations for the Product Marketplace (Module C)[cite: 18].
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Inherits save(), findAll(), findById(), and deleteById() from JpaRepository
}