package com.task.springboottask.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private UUID id;
    private String name;
    private String brand;
    private Double price;
    private Integer quantity;

    public Product(String name, String brand, Double price, Integer quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }
}
