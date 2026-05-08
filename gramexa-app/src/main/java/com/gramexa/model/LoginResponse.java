package com.gramexa.model;

public class LoginResponse {

  private String token;
  private String name;
  private String mobile;
  private String role;

  public LoginResponse(String token, String name, String mobile , String role) {
    this.token = token;
    this.name = name;
    this.mobile = mobile;
    this.role = role;
  }


  public String getToken() { return token; }
  public String getName() { return name; }
  public String getMobile() { return mobile; }

  public String getRole() {
    return role;
  }
}