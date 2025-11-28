package com.SARZATA_.LAB7.controller;

import com.SARZATA_.LAB7.Model.Product;
import com.SARZATA_.LAB7.Service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

// @Controller is used here as a standard Spring component,
// specifically for GraphQL controllers which don't need @ResponseBody.
@Controller
public class ProductGraphqlController {

    private final ProductService productService;

    // Inject the ProductService using constructor injection.
    public ProductGraphqlController(ProductService productService) {
        this.productService = productService;
    }

    // --- QUERY MAPPINGS (READ OPERATIONS) ---

    /**
     * Maps to the 'allProducts' field in the Query type.
     * GraphQL Query Example: { allProducts { id name price } }
     */
    @QueryMapping
    public List<Product> allProducts() {
        return productService.findAll();
    }

    /**
     * Maps to the 'productById' field in the Query type.
     * GraphQL Query Example: { productById(id: 1) { id name } }
     *
     * @param id The ID of the product to fetch, mapped from the GraphQL argument.
     * @return The Product if found, or null (which GraphQL handles as an error for a non-nullable field, or null if the field is nullable).
     */
    @QueryMapping
    public Product productById(@Argument Long id) {
        // Optional is mapped automatically by Spring GraphQL:
        // if present, it returns the Product; if empty, it returns null.
        return productService.findById(id).orElse(null);
    }

    // --- MUTATION MAPPINGS (WRITE OPERATIONS) ---

    /**
     * Maps to the 'createProduct' field in the Mutation type.
     * GraphQL Mutation Example:
     * mutation { createProduct(product: { name: "New Keyboard", price: 99.99 }) { id name } }
     *
     * @param product The ProductInput object, automatically mapped to a Product instance by the argument name.
     * @return The newly created Product.
     */
    @MutationMapping
    public Product createProduct(@Argument Product product) {
        // The 'product' argument is a Product object created from the ProductInput.
        // We use the existing service method to save it.
        return productService.save(product);
    }

    /**
     * Maps to the 'updateProduct' field in the Mutation type.
     * GraphQL Mutation Example:
     * mutation { updateProduct(id: 1, product: { name: "Updated Laptop Pro", price: 1599.99 }) { id name } }
     */
    @MutationMapping
    public Optional<Product> updateProduct(@Argument Long id, @Argument Product product) {
        // The 'product' argument is the new details from ProductInput.
        return productService.update(id, product);
    }

    /**
     * Maps to the 'deleteProduct' field in the Mutation type.
     * GraphQL Mutation Example:
     * mutation { deleteProduct(id: 4) }
     */
    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.delete(id);
    }
}