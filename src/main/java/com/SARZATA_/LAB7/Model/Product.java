package com.SARZATA_.LAB7.Model;

import lombok.Data; // Using Lombok for boilerplate code
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// @Data automatically generates getters, setters, toString(),
// hashCode(), and equals() for all fields. [cite: 31]
@Data
@NoArgsConstructor // Lombok's way of creating a default constructor
@AllArgsConstructor // Lombok's way of creating a constructor with all arguments
public class Product {

    // Unique identifier (required for various API operations) [cite: 16]
    private Long id;

    // Name of the product [cite: 16]
    private String name;

    // Price of the product [cite: 16]
    private Double price;
}