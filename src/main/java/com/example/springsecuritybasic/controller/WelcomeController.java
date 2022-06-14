package com.example.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @GetMapping("/read-write")
  public String allowWriteAuth() {
    return "You re allowed to write";
  }

  @GetMapping("/read-only")
  public String allowReadAuth() {
    return "You re allowed to read";
  }

  @GetMapping("/secured")
  public String allowAnthenticatedUser() {
    return "Hello, You have been authenticated";
  }

  @GetMapping("/not-secured")
  public String allowAll() {
    return "Success without authentication";
  }
}
