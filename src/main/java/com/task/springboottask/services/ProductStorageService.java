package com.task.springboottask.services;

import com.task.springboottask.model.Product;

import java.util.List;

public interface ProductStorageService {
    void save(Product product);

    void update(Product product);

    void remove(Product product);

    List<Product> getAllProducts();

    List<Product> getAllLeftovers();

    Product getProductByName(String name);

    Product getProductByBrand(String brand);
}
