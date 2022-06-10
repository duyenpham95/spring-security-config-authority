package com.example.springsecuritybasic.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WelcomeController.class)
public class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "duyen@gmail.com", password = "123")
    void givenAnthenticatedUser_whenTriggerApi_thenSuccess() throws Exception {
        mockMvc.perform(get("/secured")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "duyen@gmail.com", password = "1234")
    void givenWrong_whenTriggerApi_thenThrow401() throws Exception {
        mockMvc.perform(get("/secured")).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"READ"})
    void givenReadAuthority_whenRead_thenSuccess() throws Exception {
        mockMvc.perform(get("/read-only")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "duyen@gmail.com", password = "123")
    void givenReadAuthority_whenWrite_thenThrow403() throws Exception {
        mockMvc.perform(get("/read-write")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "duyen_2@gmail.com", password = "123")
    void givenWriteAuthority_whenWrite_thenSuccess() throws Exception {
        mockMvc.perform(get("/read-write")).andExpect(status().isOk());
    }
}
