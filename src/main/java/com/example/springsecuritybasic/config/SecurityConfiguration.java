package com.example.springsecuritybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers("/read-write")
        .hasAuthority("WRITE")
        .antMatchers("/read-only")
        .hasAuthority("READ")
        .antMatchers("/secured")
        .authenticated()
        .antMatchers("/not-secured")
        .permitAll()
        .and()
        .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
