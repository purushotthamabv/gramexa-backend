package com.gramexa.service;

import com.gramexa.entity.Products;
import com.gramexa.repository.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

  @Autowired
  private ProductsRepository productsRepository;

  // ADD PRODUCT
  public Products addProduct(Products product) {
    return productsRepository.save(product);
  }

  // GET ALL PRODUCTS
  public List<Products> getAllProducts() {
    return productsRepository.findAll();
  }

  // GET PRODUCT BY ID
  public Products getProductById(Long id) {
    return productsRepository.findById(id).orElse(null);
  }

  // DELETE PRODUCT
  public void deleteProduct(Long id) {
    productsRepository.deleteById(id);
  }

  // UPDATE PRODUCT
  public Products updateProduct(Long id, Products updatedProduct) {

    Products existingProduct =
            productsRepository.findById(id).orElse(null);

    if (existingProduct != null) {

      existingProduct.setProductName(
              updatedProduct.getProductName());

      existingProduct.setProductCategory(
              updatedProduct.getProductCategory());

      existingProduct.setProductBrand(
              updatedProduct.getProductBrand());

      existingProduct.setProductPrice(
              updatedProduct.getProductPrice());

      existingProduct.setOfferPrice(
              updatedProduct.getOfferPrice());

      existingProduct.setStockQuantity(
              updatedProduct.getStockQuantity());

      existingProduct.setProductUnit(
              updatedProduct.getProductUnit());

      existingProduct.setProductDescription(
              updatedProduct.getProductDescription());

      existingProduct.setProductImage(
              updatedProduct.getProductImage());

      existingProduct.setProductImageUrl(
              updatedProduct.getProductImageUrl());

      existingProduct.setProductStatus(
              updatedProduct.getProductStatus());

      existingProduct.setFeatured(
              updatedProduct.getFeatured());

      existingProduct.setDeliveryAvailable(
              updatedProduct.getDeliveryAvailable());

      return productsRepository.save(existingProduct);
    }

    return null;
  }
}
