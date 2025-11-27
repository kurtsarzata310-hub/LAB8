package com.SARZATA_.LAB7.Service;

import com.SARZATA_.LAB7.Model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// @Service registers this class as a business logic component. [cite: 35]
@Service
public class ProductService {

    // Using a ConcurrentHashMap to simulate the database with thread-safety. [cite: 36]
    private final Map<Long, Product> inventory = new ConcurrentHashMap<>();

    // To simulate an auto-incrementing database ID. [cite: 65]
    private final AtomicLong nextId = new AtomicLong(4); // Start after the initial mock products

    public ProductService() {
        // Initialize with at least three mock products. [cite: 37]
        inventory.put(1L, new Product(1L, "Laptop Pro", 1499.99));
        inventory.put(2L, new Product(2L, "Wireless Mouse X", 29.50));
        inventory.put(3L, new Product(3L, "4K Monitor 32", 450.00));
    }

    // --- CRUD Operations to be used by the Controller ---

    // READ ALL Products (GET)
    public List<Product> findAll() {
        return new ArrayList<>(inventory.values());
    }

    // READ ONE Product by ID (GET)
    // Optional is used for robust error handling (present or not found).
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(inventory.get(id));
    }

    // CREATE a new Product (POST)
    public Product save(Product product) {
        // Assign a new, unique ID to the product. [cite: 65]
        product.setId(nextId.getAndIncrement());
        inventory.put(product.getId(), product);
        return product;
    }

    // UPDATE an existing Product (PUT)
    public Optional<Product> update(Long id, Product updatedProduct) {
        if (inventory.containsKey(id)) {
            updatedProduct.setId(id); // Ensure the ID from the path is used
            inventory.put(id, updatedProduct);
            return Optional.of(updatedProduct);
        }
        // Return empty if ID is not found (for 404 handling). [cite: 71]
        return Optional.empty();
    }

    // DELETE a Product (DELETE)
    public boolean delete(Long id) {
        return inventory.remove(id) != null;
    }
}