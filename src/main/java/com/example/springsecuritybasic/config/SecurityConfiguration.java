package com.example.springsecuritybasic.config;

import com.example.springsecuritybasic.filter.AuthoritiesLoggingAfterFilter;
import com.example.springsecuritybasic.filter.AuthoritiesLoggingAtFilter;
import com.example.springsecuritybasic.filter.RequestValidatorBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers("/not-secured")
        .permitAll()
        .antMatchers("/secured")
        .authenticated()
        .antMatchers("/read-only")
        .hasAuthority("READ")
        .antMatchers("/read-write")
        .hasAuthority("WRITE")
        .antMatchers("/user-role")
        .hasRole("USER")
        .antMatchers("/admin-role")
        .hasRole("ADMIN")
        .and()
        .addFilterBefore(new RequestValidatorBeforeFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
        .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
