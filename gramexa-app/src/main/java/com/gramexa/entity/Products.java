package com.gramexa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Products {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String product_name;

  private Double product_price;

  private String product_description;

  @Lob
  private byte[] product_image;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProduct_name() {
    return product_name;
  }

  public void setProduct_name(String product_name) {
    this.product_name = product_name;
  }

  public Double getProduct_price() {
    return product_price;
  }

  public void setProduct_price(Double product_price) {
    this.product_price = product_price;
  }

  public String getProduct_description() {
    return product_description;
  }

  public void setProduct_description(String product_description) {
    this.product_description = product_description;
  }

  public byte[] getProduct_image() {
    return product_image;
  }

  public void setProduct_image(byte[] product_image) {
    this.product_image = product_image;
  }
}
