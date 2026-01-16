package com.infosys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.OrderService;
import com.infosys.entity.Order;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/place/{userId}/{productId}/{quantity}")
    public Order placeOrder(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        return service.placeOrder(userId, productId, quantity);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return service.getUserOrders(userId);
    }
}
