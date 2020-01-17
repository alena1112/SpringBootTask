package com.task.springboottask.services;

import com.task.springboottask.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductStorageService {
    void save(Product product);

    boolean update(Product product);

    boolean remove(UUID id);

    List<Product> getAllProducts();

    List<Product> getAllLeftovers();

    Product getProductByName(String name);

    Product getProductByBrand(String brand);
}
