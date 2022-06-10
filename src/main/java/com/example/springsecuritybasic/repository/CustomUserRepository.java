package com.example.springsecuritybasic.repository;

import com.example.springsecuritybasic.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    List<CustomUser> findByEmail(String email);
}
