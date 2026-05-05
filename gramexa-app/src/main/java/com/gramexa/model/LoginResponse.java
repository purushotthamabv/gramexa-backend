package com.gramexa.model;

public class LoginResponse {

  private String token;
  private String name;
  private String mobile;

  public LoginResponse(String token, String name, String mobile) {
    this.token = token;
    this.name = name;
    this.mobile = mobile;
  }

  public String getToken() { return token; }
  public String getName() { return name; }
  public String getMobile() { return mobile; }
}