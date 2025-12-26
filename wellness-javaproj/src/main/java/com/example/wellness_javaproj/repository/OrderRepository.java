package com.example.wellness_javaproj.repository;

import com.example.wellness_javaproj.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Handles order management and secure payment records (Module F)[cite: 21].
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Requirement: User dashboard with product orders history[cite: 40].
     * @param userId The ID of the user whose orders we want to retrieve.
     * @return A list of orders belonging to the specified user.
     */
    List<Order> findByUserId(Long userId);
}