package com.wellness.backend.repository;

import com.wellness.backend.model.Order;
import com.wellness.backend.model.OrderStatus;
import com.wellness.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // USER ORDERS
    // Without pagination (rarely used)
    List<Order> findByUser(User user);

    // With pagination (My Orders)
    Page<Order> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    // Secure single order access
    Optional<Order> findByIdAndUser(Long id, User user);

    // ADMIN / STATUS
    List<Order> findByStatus(OrderStatus status);
}
