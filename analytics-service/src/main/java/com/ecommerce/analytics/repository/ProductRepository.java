package com.ecommerce.analytics.repository;

import com.ecommerce.analytics.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity < :threshold ORDER BY p.stockQuantity ASC")
    List<Product> findLowStockProducts(int threshold);

    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    List<Object[]> countProductsByCategory();
}
