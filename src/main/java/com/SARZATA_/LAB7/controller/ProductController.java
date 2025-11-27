package com.SARZATA_.LAB7.controller;

import com.SARZATA_.LAB7.Model.Product;
import com.SARZATA_.LAB7.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// @RestController combines @Controller and @ResponseBody [cite: 43]
@RestController
// Base path for all endpoints in this controller [cite: 44]
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Inject the ProductService using constructor injection. [cite: 45]
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // --- CRUD Endpoints Implementation ---

    // 1. READ ALL Products
    // MAPPING: GET /api/products [cite: 48]
    // RESPONSE: 200 OK [cite: 57]
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        // Returns the list with HTTP status 200 OK. [cite: 57]
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 2. READ ONE Product
    // MAPPING: GET /api/products/{id} [cite: 48]
    // RESPONSE: 200 OK or 404 Not Found [cite: 48, 62]
    @GetMapping("/{id}")
    // @PathVariable captures the ID from the URL path. [cite: 59]
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            // Success: return product object and 200 OK. [cite: 60]
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            // Error Handling: return 404 Not Found explicitly via ResponseEntity. [cite: 62]
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. CREATE a new Product
    // MAPPING: POST /api/products [cite: 48]
    // RESPONSE: 201 Created [cite: 66]
    @PostMapping
    // @RequestBody receives the Product data from the request body. [cite: 64]
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        Product createdProduct = productService.save(newProduct);
        // Success: Return the newly created product and mandatory 201 Created. [cite: 66]
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // 4. UPDATE an existing Product
    // MAPPING: PUT /api/products/{id} [cite: 48]
    // RESPONSE: 200 OK or 404 Not Found [cite: 48]
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        // Accept both path variable ID and request body data. [cite: 69]
        Optional<Product> updatedProduct = productService.update(id, productDetails);

        if (updatedProduct.isPresent()) {
            // Success: return updated product and 200 OK. [cite: 70]
            return new ResponseEntity<>(updatedProduct.get(), HttpStatus.OK);
        } else {
            // Error Handling: return 404 Not Found. [cite: 71]
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. DELETE a Product
    // MAPPING: DELETE /api/products/{id} [cite: 48]
    // RESPONSE: 200 OK (or 204 No Content) [cite: 49]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.delete(id);

        if (deleted) {
            // Using 204 No Content for successful deletion (no body returned).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // If ID doesn't exist, we can treat it as successful (idempotency) or use 404.
            // Sticking to 204 is common for idempotent DELETEs.
            // For this lab, let's treat a successful removal or non-existent ID as 204 No Content for simplicity.
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}