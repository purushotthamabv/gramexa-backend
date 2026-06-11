package com.gramexa.model;

public class OrderItemResponse {

  private Long id;
  private Long productId;
  private String productName;
  private Double unitPrice;
  private Integer quantity;
  private Double lineTotal;

  public OrderItemResponse(Long id, Long productId, String productName, Double unitPrice, Integer quantity, Double lineTotal) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
    this.lineTotal = lineTotal;
  }

  public Long getId() { return id; }
  public Long getProductId() { return productId; }
  public String getProductName() { return productName; }
  public Double getUnitPrice() { return unitPrice; }
  public Integer getQuantity() { return quantity; }
  public Double getLineTotal() { return lineTotal; }
}
