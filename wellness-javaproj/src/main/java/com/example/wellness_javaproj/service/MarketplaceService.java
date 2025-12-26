package com.example.wellness_javaproj.service;

import com.example.wellness_javaproj.model.*;
import com.example.wellness_javaproj.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketplaceService {
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    public MarketplaceService(ProductRepository productRepo, OrderRepository orderRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    public List<Product> getAllProducts() { return productRepo.findAll(); } //

    public Order placeOrder(Long userId, Long productId, Integer quantity) {
        User user = userRepo.findById(userId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        if (product.getStock() < quantity) throw new RuntimeException("Out of stock");

        product.setStock(product.getStock() - quantity); // Update stock [cite: 84]
        productRepo.save(product);

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalAmount(product.getPrice() * quantity);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        return orderRepo.save(order);
    }
}