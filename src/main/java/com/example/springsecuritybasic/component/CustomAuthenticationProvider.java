package com.example.springsecuritybasic.component;

import com.example.springsecuritybasic.model.CustomUser;
import com.example.springsecuritybasic.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired private CustomUserRepository customUserRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String inputUsername = authentication.getName();
    String inputPassword = authentication.getCredentials().toString();
    List<CustomUser> customUserList = customUserRepository.findByEmail(inputUsername);

    if (customUserList.size() > 0) {
      String encodedPassword = customUserList.get(0).getPwd();
      if (passwordEncoder.matches(inputPassword, encodedPassword)) {
        List<GrantedAuthority> authorities =
            customUserList.get(0).getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(inputUsername, inputPassword, authorities);
      } else {
        throw new BadCredentialsException("Invalid password");
      }
    } else {
      throw new BadCredentialsException("No user registered with this details");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
