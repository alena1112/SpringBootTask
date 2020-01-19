package com.task.springboottask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.springboottask.mvc.model.Product;
import com.task.springboottask.rest.TokenResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(JUnit4.class)
public class ProductTest {
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void clear() throws Exception {
        TokenResponse adminToken = getAdminToken();
        List allProducts = getAllProducts("all", adminToken);
        if (allProducts != null) {
            allProducts.forEach(fields -> {
                try {
                    deleteProduct(((Map<String, String>) fields).get("id"), adminToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void createProductTest() throws Exception {
        TokenResponse adminToken = getAdminToken();
        Product product = new Product("Milka", "Alpen Gold", 100.0, 5);
        createProduct(product, adminToken);

        Product savedProduct = getProductByName("Milka", adminToken);
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals(savedProduct.getBrand(), product.getBrand());
        assertEquals(savedProduct.getPrice(), product.getPrice());
        assertEquals(savedProduct.getQuantity(), product.getQuantity());
    }

    @Test
    public void updateProductTest() throws Exception {
        TokenResponse adminToken = getAdminToken();
        Product product = new Product("Milka", "Alpen Gold", 100.0, 5);
        createProduct(product, adminToken);

        product = getProductByName("Milka", adminToken);
        assertNotNull(product);

        product.setPrice(200.0);
        updateProduct(product, adminToken);

        Product updatedProduct = getProductByBrand("Alpen Gold", adminToken);
        assertNotNull(updatedProduct);
        assertEquals(updatedProduct.getPrice(), product.getPrice());
    }

    @Test
    public void deleteProductTest() throws Exception {
        TokenResponse adminToken = getAdminToken();
        Product product = new Product("Milka", "Alpen Gold", 100.0, 5);
        createProduct(product, adminToken);

        product = getProductByName("Milka", adminToken);
        assertNotNull(product);
        deleteProduct(product.getId().toString(), adminToken);

        product = getProductByBrand("Alpen Gold", adminToken);
        assertNull(product);
    }

    @Test
    public void getAllProductsTest() throws Exception {
        TokenResponse adminToken = getAdminToken();
        createProduct(new Product("Milka", "Alpen Gold", 100.0, 5), adminToken);
        createProduct(new Product("Tuk", "Some factory", 50.0, 4), adminToken);
        createProduct(new Product("Oreo", "Oreo", 20.0, 3), adminToken);

        List allProducts = getAllProducts("all", adminToken);
        assertNotNull(allProducts);
        assertEquals(allProducts.size(), 3);

        List leftoverProducts = getAllProducts("leftovers", adminToken);
        assertNotNull(leftoverProducts);
        assertEquals(leftoverProducts.size(), 2);
    }

    private void createProduct(Product product, TokenResponse token) throws Exception {
        mockMvc.perform(get("/product/new")
                .param("name", product.getName())
                .param("brand", product.getBrand())
                .param("price", String.valueOf(product.getPrice()))
                .param("quantity", String.valueOf(product.getQuantity()))
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists());
    }

    private void updateProduct(Product product, TokenResponse token) throws Exception {
        mockMvc.perform(get("/product/update")
                .param("id", product.getId().toString())
                .param("name", product.getName())
                .param("brand", product.getBrand())
                .param("price", String.valueOf(product.getPrice()))
                .param("quantity", String.valueOf(product.getQuantity()))
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    private void deleteProduct(String id, TokenResponse token) throws Exception {
        mockMvc.perform(get("/product/remove")
                .param("id", id)
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    private Product getProductByName(String name, TokenResponse token) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/product/getByName")
                .param("name", name)
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        if (content != null && !content.equals("")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, Product.class);
        } else {
            return null;
        }
    }

    private Product getProductByBrand(String brand, TokenResponse token) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/product/getByBrand")
                .param("brand", brand)
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        if (content != null && !content.equals("")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, Product.class);
        } else {
            return null;
        }
    }

    private List getAllProducts(String query, TokenResponse token) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/product/" + query)
                .header("Authorization", "Bearer " + token.getToken()))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        if (content != null && !content.equals("")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, List.class);
        } else {
            return null;
        }
    }

    private TokenResponse getAdminToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/authenticate")
                .param("username", "admin")
                .param("password", "root"))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, TokenResponse.class);
    }
}
