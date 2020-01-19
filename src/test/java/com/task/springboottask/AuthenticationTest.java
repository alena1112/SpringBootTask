package com.task.springboottask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthentication() throws Exception {
        mockMvc.perform(get("/authenticate")
                .param("username", "user")
                .param("password", "user"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString("Wrong credentials")));
        mockMvc.perform(get("/authenticate")
                .param("username", "admin")
                .param("password", "root"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("token").exists());
        mockMvc.perform(get("/authenticate")
                .param("username", "client")
                .param("password", "client"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("token").exists());
    }
}
