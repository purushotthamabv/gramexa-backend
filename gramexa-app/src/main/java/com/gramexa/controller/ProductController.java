package com.gramexa.controller;

import com.gramexa.entity.Products;
import com.gramexa.service.ProductsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

  @Autowired
  private ProductsService productsService;

  // ADD PRODUCT
  @PostMapping("/add-product")
  public Products addProduct(@RequestBody Products product) {
    return productsService.addProduct(product);
  }

  // GET ALL PRODUCTS
  @GetMapping("/all-products")
  public List<Products> getAllProducts() {
    return productsService.getAllProducts();
  }

  // GET PRODUCT BY ID
  @GetMapping("/{id}")
  public Products getProductById(@PathVariable Long id) {
    return productsService.getProductById(id);
  }

  // DELETE PRODUCT
  @DeleteMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productsService.deleteProduct(id);

    return "Product deleted successfully";
  }

  // UPDATE PRODUCT
  @PutMapping("/update/{id}")
  public Products updateProduct(
          @PathVariable Long id,
          @RequestBody Products product) {

    return productsService.updateProduct(id, product);
  }
}