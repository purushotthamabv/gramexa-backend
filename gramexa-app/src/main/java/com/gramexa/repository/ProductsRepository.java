package com.gramexa.repository;

import com.gramexa.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {

  boolean existsByProductName(String productName);

}
