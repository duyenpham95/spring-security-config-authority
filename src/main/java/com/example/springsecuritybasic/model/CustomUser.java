package com.example.springsecuritybasic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class CustomUser implements Serializable {

  @Id @GeneratedValue private Long id;

  private String email;

  private String pwd;

  private String roleName;

  @JsonIgnore
  @OneToMany(mappedBy = "customUser", fetch = FetchType.EAGER)
  private Set<Authority> authorities;
}
