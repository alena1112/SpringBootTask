package com.task.springboottask.services;

import com.task.springboottask.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

//TODO create thread-safe class
public class ProductStorageServiceImpl implements ProductStorageService {
    private static final Logger log = LoggerFactory.getLogger(ProductStorageServiceImpl.class);
    private Map<UUID, Product> storage = new HashMap<>();

    @Override
    public void save(Product product) {
        storage.put(product.getId(), product);
        log.info(String.format("Product %s was saved successfully", product.getName()));
    }

    @Override
    public boolean update(Product product) {
        if (storage.containsKey(product.getId())) {
            storage.put(product.getId(), product);
            log.info(String.format("Product %s was updated successfully", product.getName()));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(UUID id) {
        if (storage.remove(id) != null) {
            log.info(String.format("Product %s was removed successfully", id));
            return true;
        } else {
            log.warn(String.format("Product %s does not exist", id));
            return false;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Product> getAllLeftovers() {
        return storage.values()
                .stream()
                .filter(product -> product.getQuantity() == null || product.getQuantity() < 5)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductByName(String name) {
        return storage.values()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Product getProductByBrand(String brand) {
        return storage.values()
                .stream()
                .filter(product -> product.getBrand() != null && product.getBrand().equals(brand))
                .findFirst()
                .orElse(null);
    }
}
