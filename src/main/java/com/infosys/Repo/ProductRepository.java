package com.infosys.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.infosys.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
