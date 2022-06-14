package com.example.springsecuritybasic.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WelcomeController.class)
public class WelcomeControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @WithMockUser
  void givenAnthenticatedUser_whenTriggerApi_thenSuccess() throws Exception {
    mockMvc.perform(get("/secured")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void givenReadAuthority_whenRead_thenSuccess() throws Exception {
    mockMvc.perform(get("/read-only")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void givenWriteAuthority_whenWrite_thenSuccess() throws Exception {
    mockMvc.perform(get("/read-write")).andExpect(status().isOk());
  }
}
