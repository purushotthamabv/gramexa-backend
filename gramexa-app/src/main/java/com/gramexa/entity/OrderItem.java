package com.gramexa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  @JsonIgnore
  private CustomerOrder order;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id", nullable = false)
  private Products product;

  private String productName;
  private Double unitPrice;
  private Integer quantity;
  private Double lineTotal;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public CustomerOrder getOrder() { return order; }
  public void setOrder(CustomerOrder order) { this.order = order; }
  public Products getProduct() { return product; }
  public void setProduct(Products product) { this.product = product; }
  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }
  public Double getUnitPrice() { return unitPrice; }
  public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public Double getLineTotal() { return lineTotal; }
  public void setLineTotal(Double lineTotal) { this.lineTotal = lineTotal; }
}
