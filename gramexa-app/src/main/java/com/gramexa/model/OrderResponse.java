package com.gramexa.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

  private Long id;
  private Double totalAmount;
  private String status;
  private String paymentMode;
  private String deliveryName;
  private String deliveryMobile;
  private String deliveryAddress;
  private String deliveryPincode;
  private LocalDateTime createdAt;
  private List<OrderItemResponse> items;

  public OrderResponse(Long id, Double totalAmount, String status, String paymentMode, String deliveryName, String deliveryMobile, String deliveryAddress, String deliveryPincode, LocalDateTime createdAt, List<OrderItemResponse> items) {
    this.id = id;
    this.totalAmount = totalAmount;
    this.status = status;
    this.paymentMode = paymentMode;
    this.deliveryName = deliveryName;
    this.deliveryMobile = deliveryMobile;
    this.deliveryAddress = deliveryAddress;
    this.deliveryPincode = deliveryPincode;
    this.createdAt = createdAt;
    this.items = items;
  }

  public Long getId() { return id; }
  public Double getTotalAmount() { return totalAmount; }
  public String getStatus() { return status; }
  public String getPaymentMode() { return paymentMode; }
  public String getDeliveryName() { return deliveryName; }
  public String getDeliveryMobile() { return deliveryMobile; }
  public String getDeliveryAddress() { return deliveryAddress; }
  public String getDeliveryPincode() { return deliveryPincode; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public List<OrderItemResponse> getItems() { return items; }
}
