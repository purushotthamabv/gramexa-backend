package com.gramexa.repository;

import com.gramexa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  // Check if email already exists
  boolean existsByEmail(String email);

  // Check if mobile number already exists
  boolean existsByMobileNumber(String mobileNumber);

  // Find user by email (used in login later)
  Optional<User> findByEmail(String email);

  // Find user by mobile number (important for rural users login)
  Optional<User> findByMobileNumber(String mobileNumber);

  Optional<User> findByEmailOrMobileNumber(String email, String mobileNumber);
}