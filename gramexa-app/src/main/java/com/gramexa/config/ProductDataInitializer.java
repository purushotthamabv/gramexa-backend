package com.gramexa.config;

import com.gramexa.entity.Products;
import com.gramexa.repository.ProductsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDataInitializer implements CommandLineRunner {

  private final ProductsRepository productsRepository;

  public ProductDataInitializer(ProductsRepository productsRepository) {
    this.productsRepository = productsRepository;
  }

  @Override
  public void run(String... args) {
    List.of(
            createProduct(
                    "Vitamin C Tablets",
                    "MEDICINE",
                    "HealthPlus",
                    399.0,
                    249.0,
                    30,
                    "60 tablets",
                    "Daily immunity support with vitamin C and zinc.",
                    "https://images.unsplash.com/photo-1622484212850-eb596d769edc",
                    true
            ),
            createProduct(
                    "Organic Apple Juice",
                    "HEALTHY FOOD",
                    "FarmFresh",
                    180.0,
                    120.0,
                    24,
                    "1 bottle",
                    "Fresh organic apple juice for daily nutrition.",
                    "https://images.unsplash.com/photo-1607619056574-7b8d3ee536b2",
                    true
            ),
            createProduct(
                    "Organic Vegetables Pack",
                    "GROCERY",
                    "Village Basket",
                    699.0,
                    499.0,
                    18,
                    "1 pack",
                    "Seasonal vegetables sourced from local farms.",
                    "https://images.unsplash.com/photo-1542838132-92c53300491e",
                    true
            ),
            createProduct(
                    "First Aid Kit",
                    "HEALTHCARE",
                    "CareBox",
                    799.0,
                    599.0,
                    15,
                    "1 kit",
                    "Essential first aid supplies for home and travel.",
                    "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae",
                    false
            ),
            createProduct(
                    "Protein Nutrition Mix",
                    "NUTRITION",
                    "NutriLife",
                    499.0,
                    349.0,
                    22,
                    "500 g",
                    "Balanced nutrition mix for everyday wellness.",
                    "https://images.unsplash.com/photo-1579722821273-0f6c7d44362f",
                    false
            )
    ).forEach(this::saveIfMissing);
  }

  private void saveIfMissing(Products product) {
    if (!productsRepository.existsByProductName(product.getProductName())) {
      productsRepository.save(product);
    }
  }

  private Products createProduct(
          String name,
          String category,
          String brand,
          Double price,
          Double offerPrice,
          Integer stock,
          String unit,
          String description,
          String imageUrl,
          Boolean featured
  ) {
    Products product = new Products();
    product.setProductName(name);
    product.setProductCategory(category);
    product.setProductBrand(brand);
    product.setProductPrice(price);
    product.setOfferPrice(offerPrice);
    product.setStockQuantity(stock);
    product.setProductUnit(unit);
    product.setProductDescription(description);
    product.setProductImageUrl(imageUrl);
    product.setFeatured(featured);
    product.setDeliveryAvailable(true);
    product.setProductStatus("ACTIVE");

    return product;
  }
}
