package com.gramexa.model;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
  @NotBlank(message = "Email or Mobile is required")
  private String username;   // 🔥 single field for both

  @NotBlank(message = "Password is required")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
