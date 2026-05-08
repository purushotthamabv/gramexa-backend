package com.gramexa.controller;

import com.gramexa.entity.Products;
import com.gramexa.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")

public class ProductController {

  @Autowired
  private ProductsService productsService;

  @PostMapping("/add-product")
  public Products addProduct(@RequestBody Products product) {
    return productsService.addProduct(product);
  }


}
