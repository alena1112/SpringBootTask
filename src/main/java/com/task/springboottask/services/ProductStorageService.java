package com.task.springboottask.services;

import com.task.springboottask.mvc.model.Product;
import com.task.springboottask.mvc.model.UserRole;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.UUID;

/**
 * Class for storage and manage products
 *
 */
public interface ProductStorageService {
    /**
     * Create new product only for users with Admin role
     */
    @Secured(UserRole.ADMIN)
    void save(Product product);

    /**
     * Update exist product only for users with Admin role
     * @return true if product was updated successfully. Otherwise false
     */
    @Secured(UserRole.ADMIN)
    boolean update(Product product);

    /**
     * Delete exist product only for users with Admin role
     * @return true if product was deleted successfully. Otherwise false
     */
    @Secured(UserRole.ADMIN)
    boolean remove(UUID id);

    /**
     * @return all products for users with Admin or read only role
     */
    @Secured({UserRole.ADMIN, UserRole.READ_ONLY})
    List<Product> getAllProducts();

    /**
     * @return all products which quantity is less than 5. Method works for users with Admin or read only role
     */
    @Secured({UserRole.ADMIN, UserRole.READ_ONLY})
    List<Product> getAllLeftovers();

    /**
     * @return exist product by name. Method works for users with Admin or read only role
     */
    @Secured({UserRole.ADMIN, UserRole.READ_ONLY})
    Product getProductByName(String name);

    /**
     * @return exist product by brand. Method works for users with Admin or read only role
     */
    @Secured({UserRole.ADMIN, UserRole.READ_ONLY})
    Product getProductByBrand(String brand);
}
