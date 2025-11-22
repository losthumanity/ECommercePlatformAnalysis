package com.ecommerce.analytics.repository;

import com.ecommerce.analytics.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.saleDate BETWEEN :start AND :end")
    BigDecimal getTotalSalesBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT p.category, SUM(s.totalAmount) FROM Sale s JOIN s.product p " +
           "WHERE s.saleDate BETWEEN :start AND :end GROUP BY p.category ORDER BY SUM(s.totalAmount) DESC")
    List<Object[]> getSalesByCategory(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT p.name, SUM(s.quantity) as totalQty FROM Sale s JOIN s.product p " +
           "WHERE s.saleDate BETWEEN :start AND :end GROUP BY p.id, p.name ORDER BY totalQty DESC")
    List<Object[]> getTopSellingProducts(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT DATE(s.saleDate), SUM(s.totalAmount) FROM Sale s " +
           "WHERE s.saleDate BETWEEN :start AND :end GROUP BY DATE(s.saleDate) ORDER BY DATE(s.saleDate)")
    List<Object[]> getDailySales(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.status = :status")
    Long countByStatus(@Param("status") String status);
}
