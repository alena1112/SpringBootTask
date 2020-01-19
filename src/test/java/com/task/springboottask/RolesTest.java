package com.task.springboottask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.springboottask.rest.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RolesTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createProductTest() throws Exception {
        mockMvc.perform(get("/product/new"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/new")
                .param("name", "Milka")
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/new")
                .param("name", "Milka")
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().is(403));
    }

    @Test
    public void updateProductTest() throws Exception {
        mockMvc.perform(get("/product/update"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/update")
                .param("id", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/update")
                .param("id", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().is(403));
    }

    @Test
    public void deleteProductTest() throws Exception {
        mockMvc.perform(get("/product/remove"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/remove")
                .param("id", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/remove")
                .param("id", UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().is(403));
    }

    @Test
    public void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/product/all"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/all")
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/all")
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void getLeftoversProductsTest() throws Exception {
        mockMvc.perform(get("/product/leftovers"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/leftovers")
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/leftovers")
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductByNameTest() throws Exception {
        mockMvc.perform(get("/product/getByName"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/getByName")
                .param("name", "name")
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/getByName")
                .param("name", "name")
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductByBrandTest() throws Exception {
        mockMvc.perform(get("/product/getByBrand"))
                .andExpect(status().is(401));

        TokenResponse adminToken = getAdminToken();
        mockMvc.perform(get("/product/getByBrand")
                .param("brand", "brand")
                .header("Authorization", "Bearer " + adminToken.getToken()))
                .andExpect(status().isOk());

        TokenResponse clientToken = getClientToken();
        mockMvc.perform(get("/product/getByBrand")
                .param("brand", "brand")
                .header("Authorization", "Bearer " + clientToken.getToken()))
                .andExpect(status().isOk());
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

    private TokenResponse getClientToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/authenticate")
                .param("username", "client")
                .param("password", "client"))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, TokenResponse.class);
    }
}
