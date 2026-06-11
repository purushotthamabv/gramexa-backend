package com.gramexa.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CheckoutRequest {

  @NotBlank(message = "Delivery name is required")
  private String deliveryName;

  @NotBlank(message = "Delivery mobile is required")
  @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number (must be 10 digits starting with 6-9)")
  private String deliveryMobile;

  @NotBlank(message = "Delivery address is required")
  private String deliveryAddress;

  @NotBlank(message = "Delivery pincode is required")
  @Pattern(regexp = "^\\d{6}$", message = "Pincode must be 6 digits")
  private String deliveryPincode;

  public String getDeliveryName() { return deliveryName; }
  public void setDeliveryName(String deliveryName) { this.deliveryName = deliveryName; }
  public String getDeliveryMobile() { return deliveryMobile; }
  public void setDeliveryMobile(String deliveryMobile) { this.deliveryMobile = deliveryMobile; }
  public String getDeliveryAddress() { return deliveryAddress; }
  public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
  public String getDeliveryPincode() { return deliveryPincode; }
  public void setDeliveryPincode(String deliveryPincode) { this.deliveryPincode = deliveryPincode; }
}
