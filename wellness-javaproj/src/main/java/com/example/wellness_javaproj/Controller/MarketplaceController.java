package com.example.wellness_javaproj.Controller;

import com.example.wellness_javaproj.model.*;
import com.example.wellness_javaproj.service.MarketplaceService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class MarketplaceController {
    private final MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @GetMapping
    public List<Product> listProducts() { return marketplaceService.getAllProducts(); }

    @PostMapping("/purchase")
    public Order purchase(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        return marketplaceService.placeOrder(userId, productId, quantity);
    }
}