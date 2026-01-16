package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.OrderRepository;
import com.infosys.Repo.ProductRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.entity.Order;
import com.infosys.entity.Product;
import com.infosys.entity.User;

@Service
public class OrderService {

    @Autowired
    private OrderRepository or;

    @Autowired
    private UserRepository ur;

    @Autowired
    private ProductRepository pr;

    public Order placeOrder(Long userId, Long productId, Integer quantity) {

        User user = ur.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = pr.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalAmount(product.getPrice() * quantity);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        product.setStock(product.getStock() - quantity);
        pr.save(product);

        return or.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return or.findByUserId(userId);
    }
}
