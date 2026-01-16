package com.infosys.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.ProductRepository;
import com.infosys.entity.Product;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // Add product
    public Product addProduct(Product product) {
        return repo.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    // Get product by ID
    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Delete product
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }
}
