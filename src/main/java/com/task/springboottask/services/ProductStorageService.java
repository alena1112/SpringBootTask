package com.task.springboottask.services;

import com.task.springboottask.mvc.model.Product;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.UUID;

public interface ProductStorageService {
    @Secured("ROLE_ADMIN")
    void save(Product product);

    @Secured("ROLE_ADMIN")
    boolean update(Product product);

    @Secured("ROLE_ADMIN")
    boolean remove(UUID id);

    @Secured({"ROLE_ADMIN", "ROLE_READ_ONLY"})
    List<Product> getAllProducts();

    @Secured({"ROLE_ADMIN", "ROLE_READ_ONLY"})
    List<Product> getAllLeftovers();

    @Secured({"ROLE_ADMIN", "ROLE_READ_ONLY"})
    Product getProductByName(String name);

    @Secured({"ROLE_ADMIN", "ROLE_READ_ONLY"})
    Product getProductByBrand(String brand);
}
