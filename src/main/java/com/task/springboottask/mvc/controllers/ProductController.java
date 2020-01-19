package com.task.springboottask.mvc.controllers;

import com.task.springboottask.mvc.model.Product;
import com.task.springboottask.services.ProductStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
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

    @GetMapping("/update")
    public Boolean updateProduct(@RequestParam(value = "id") UUID id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "brand", required = false) String brand,
                                 @RequestParam(value = "price", required = false) Double price,
                                 @RequestParam(value = "quantity", required = false) Integer quantity) {
        Product product = Product.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .price(price)
                .quantity(quantity)
                .build();
        return productStorageService.update(product);
    }

    @GetMapping("/remove")
    public Boolean removeProduct(@RequestParam(value = "id") UUID id) {
        return productStorageService.remove(id);
    }

    @GetMapping("/getByName")
    public Product getProductByName(@RequestParam(value = "name") String name) {
        return productStorageService.getProductByName(name);
    }

    @GetMapping("/getByBrand")
    public Product getProductByBrand(@RequestParam(value = "brand") String brand) {
        return productStorageService.getProductByBrand(brand);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productStorageService.getAllProducts();
    }

    @GetMapping("/leftovers")
    public List<Product> getAllLeftovers() {
        return productStorageService.getAllLeftovers();
    }
}
