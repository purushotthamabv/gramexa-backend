package com.gramexa.model;

public class UserProfileResponse {

  private Long id;
  private String name;
  private String email;
  private String mobileNumber;
  private String role;

  public UserProfileResponse(Long id, String name, String email, String mobileNumber, String role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.mobileNumber = mobileNumber;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getRole() {
    return role;
  }
}
