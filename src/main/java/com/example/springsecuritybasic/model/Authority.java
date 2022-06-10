package com.example.springsecuritybasic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Authority implements Serializable {

  @Id @GeneratedValue private Long id;

  private String authority;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_name", referencedColumnName = "email")
  private CustomUser customUser;
}
