package com.task.springboottask.mvc.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {
    private UUID id;
    private String name;
    private String brand;
    private Double price;
    private Integer quantity;
}
