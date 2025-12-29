package com.wellness.backend.controller;
import com.wellness.backend.model.OrderStatus;
import com.wellness.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderService service;

    public AdminOrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(service.getAllOrders(page, size));
    }

    @PutMapping("/{id}/ship")
    public ResponseEntity<?> ship(@PathVariable Long id) {
        return ResponseEntity.ok(service.markStatus(id, OrderStatus.SHIPPED));
    }

    @PutMapping("/{id}/deliver")
    public ResponseEntity<?> deliver(@PathVariable Long id) {
        return ResponseEntity.ok(service.markStatus(id, OrderStatus.DELIVERED));
    }
    
    @PutMapping("/{id}/returned")
    public ResponseEntity<?> markReturned(@PathVariable Long id) {
        return ResponseEntity.ok(
                service.markStatus(id, OrderStatus.RETURNED)
        );
    }

}
