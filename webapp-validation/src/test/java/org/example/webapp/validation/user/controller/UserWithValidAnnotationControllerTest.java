package org.example.webapp.validation.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserWithValidAnnotationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateUserWithValidParams() throws Exception {
        mockMvc.perform(post("/userWithValidAnnotation/user")
                        .param("name", "validUser")
                        .param("age", "30"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals("text/plain;charset=UTF-8", result.getResponse().getContentType());
                    assertEquals(("User updated: validUser, Age: 30"), result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testUpdateUserWithInvalidParam() throws Exception {
        mockMvc.perform(post("/userWithValidAnnotation/user")
                        .param("age", "30")) // Missing name
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertEquals("text/plain;charset=UTF-8", result.getResponse().getContentType());
                    assertEquals(("Validation errors occurred"), result.getResponse().getContentAsString());
                });
    }
}