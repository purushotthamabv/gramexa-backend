package com.gramexa.service;

import com.gramexa.entity.Products;
import com.gramexa.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
  @Autowired
  private ProductsRepository productsRepository;

  // ADD PRODUCT
  public Products addProduct(Products product) {
    return productsRepository.save(product);
  }
}
