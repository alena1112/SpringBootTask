package com.task.springboottask.controllers;

import com.task.springboottask.model.Product;
import com.task.springboottask.services.ProductStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductStorageService productStorageService;

    @GetMapping("/new")
    public Product getNewProduct(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "brand", required = false) String brand,
                                 @RequestParam(value = "price", required = false) Double price,
                                 @RequestParam(value = "quantity", required = false) Integer quantity) {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(name)
                .brand(brand)
                .price(price)
                .quantity(quantity)
                .build();
        productStorageService.save(product);
        return product;
    }
}
