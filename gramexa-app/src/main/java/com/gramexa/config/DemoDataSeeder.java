package com.gramexa.config;

import com.gramexa.entity.Products;
import com.gramexa.entity.Restaurant;
import com.gramexa.entity.RestaurantItem;
import com.gramexa.entity.User;
import com.gramexa.repository.ProductsRepository;
import com.gramexa.repository.RestaurantItemRepository;
import com.gramexa.repository.RestaurantRepository;
import com.gramexa.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@Profile("prod")
public class DemoDataSeeder {

  @Bean
  CommandLineRunner seedDemoData(
          ProductsRepository products,
          RestaurantRepository restaurants,
          RestaurantItemRepository restaurantItems,
          UserRepository users,
          PasswordEncoder passwordEncoder,
          @Value("${SEED_SUPERADMIN_EMAIL:superadmin@gramexa.com}") String adminEmail,
          @Value("${SEED_SUPERADMIN_PASSWORD:Gramexa@123}") String adminPassword
  ) {
    return args -> {
      seedProduct(products, "PROD021", "Rava Dosa", "South Indian", 100.0,
              "https://images.unsplash.com/photo-1630383249896-424e482df921");
      seedProduct(products, "PROD022", "Onion Dosa", "South Indian", 110.0,
              "https://images.unsplash.com/photo-1630383249896-424e482df921");
      seedProduct(products, "PROD023", "Set Dosa", "South Indian", 100.0,
              "https://images.unsplash.com/photo-1630383249896-424e482df921");

      Restaurant restaurant = restaurants.findByRestaurantId("REST001").orElseGet(() -> {
        Restaurant value = new Restaurant();
        value.setRestaurantId("REST001");
        value.setRestaurantName("Gramexa Kitchen");
        value.setCurrency("INR");
        value.setImageUrl("https://images.unsplash.com/photo-1517248135467-4c7edcab34c4");
        return restaurants.save(value);
      });

      seedRestaurantItem(restaurantItems, restaurant, "RESTPROD001", "Masala Dosa", "South Indian", 120.0);
      seedRestaurantItem(restaurantItems, restaurant, "RESTPROD002", "Idli Vada", "South Indian", 80.0);

      if (!users.existsByEmail(adminEmail)) {
        User admin = new User();
        admin.setName("Gramexa Super Admin");
        admin.setEmail(adminEmail);
        admin.setMobileNumber("9999999999");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole("SUPER_ADMIN");
        admin.setApproved(true);
        admin.setCreatedAt(LocalDateTime.now());
        users.save(admin);
      }
    };
  }

  private void seedProduct(ProductsRepository repository, String id, String name,
                           String category, Double price, String imageUrl) {
    if (repository.findByProductId(id).isPresent()) return;
    Products product = new Products();
    product.setProductId(id);
    product.setProductName(name);
    product.setProductCategory(category);
    product.setProductPrice(price);
    product.setOfferPrice(price);
    product.setStockQuantity(100);
    product.setProductUnit("piece");
    product.setProductDescription(name + " prepared fresh by Gramexa Kitchen.");
    product.setProductImageUrl(imageUrl);
    product.setFeatured(true);
    product.setDeliveryAvailable(true);
    repository.save(product);
  }

  private void seedRestaurantItem(RestaurantItemRepository repository, Restaurant restaurant,
                                  String id, String name, String category, Double price) {
    if (repository.findByRestaurantAndProductId(restaurant, id).isPresent()) return;
    RestaurantItem item = new RestaurantItem();
    item.setProductId(id);
    item.setName(name);
    item.setCategory(category);
    item.setPrice(price);
    item.setOfferPrice(price);
    item.setIsVegetarian(true);
    item.setIsAvailable(true);
    item.setRestaurant(restaurant);
    repository.save(item);
  }
}
