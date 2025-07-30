package org.example.webapp.validation.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUserWithValidParam() throws Exception {
        mockMvc.perform(get("/user").param("name", "testUser"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals("application/json", result.getResponse().getContentType());
                    assertTrue(result.getResponse().getContentAsString().contains("testUser"));
                });
    }

    @Test
    public void testGetUserWithInvalidParam() throws Exception {

        mockMvc.perform(get("/user"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertEquals("text/plain;charset=UTF-8", result.getResponse().getContentType());
                    assertTrue(result.getResponse().getContentAsString().contains("getUser.arg0: must not be null"));
                });

    }
}