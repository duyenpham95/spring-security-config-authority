package com.example.springsecuritybasic.filter;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.authentication.www.BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC;

public class RequestValidatorBeforeFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) servletRequest;
      HttpServletResponse res = (HttpServletResponse) servletResponse;
      String header = req.getHeader(AUTHORIZATION);
      if (header != null) {
          header = header.trim();
          if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
              byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
              byte[] decoded;
              try {
                  decoded = Base64.getDecoder().decode(base64Token);
                  String token = new String(decoded, StandardCharsets.UTF_8);
                  int delim = token.indexOf(":");
                  if (delim == -1) {
                      throw new BadCredentialsException("Invalid basic authentication token");
                  }
                  String email = token.substring(0, delim);
                  if(email.toLowerCase().contains("test")) {
                      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                      return;
                  }
              } catch (IllegalArgumentException e) {
                  throw new BadCredentialsException("Failed to decode basic authentication token");
              }
          }
      }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
