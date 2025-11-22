package com.ecommerce.analytics.service;

import com.ecommerce.analytics.dto.*;
import com.ecommerce.analytics.model.Product;
import com.ecommerce.analytics.repository.ProductRepository;
import com.ecommerce.analytics.repository.SaleRepository;
import com.ecommerce.analytics.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AnalyticsService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserActivityRepository userActivityRepository;

    /**
     * Get sales analytics by category for a given date range
     */
    @Cacheable(value = "salesByCategory", key = "#startDate + '-' + #endDate")
    public List<CategorySalesDto> getSalesByCategory(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching sales by category from {} to {}", startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<Object[]> results = saleRepository.getSalesByCategory(start, end);

        return results.stream()
                .map(result -> new CategorySalesDto(
                        (String) result[0],
                        (BigDecimal) result[1],
                        null // Can be enhanced with product count
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get top selling products for a given date range
     */
    @Cacheable(value = "topProducts", key = "#startDate + '-' + #endDate + '-' + #limit")
    public List<TopProductDto> getTopSellingProducts(LocalDate startDate, LocalDate endDate, int limit) {
        log.info("Fetching top {} selling products from {} to {}", limit, startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<Object[]> results = saleRepository.getTopSellingProducts(start, end);

        // Calculate total for percentage
        Long total = results.stream()
                .mapToLong(r -> ((Number) r[1]).longValue())
                .sum();

        return results.stream()
                .limit(limit)
                .map(result -> new TopProductDto(
                        (String) result[0],
                        ((Number) result[1]).longValue(),
                        total > 0 ? (((Number) result[1]).doubleValue() / total) * 100 : 0.0
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get daily sales for a given date range
     */
    @Cacheable(value = "dailySales", key = "#startDate + '-' + #endDate")
    public List<DailySalesDto> getDailySales(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching daily sales from {} to {}", startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<Object[]> results = saleRepository.getDailySales(start, end);

        return results.stream()
                .map(result -> new DailySalesDto(
                        ((java.sql.Date) result[0]).toLocalDate(),
                        (BigDecimal) result[1],
                        null // Can be enhanced with transaction count
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get total sales amount for a given date range
     */
    @Cacheable(value = "totalSales", key = "#startDate + '-' + #endDate")
    public BigDecimal getTotalSales(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching total sales from {} to {}", startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        BigDecimal total = saleRepository.getTotalSalesBetween(start, end);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Get inventory status with stock levels
     */
    @Cacheable(value = "inventoryStatus")
    public List<InventoryDto> getInventoryStatus() {
        log.info("Fetching inventory status");

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> {
                    String status = determineStockStatus(product.getStockQuantity());
                    return new InventoryDto(
                            product.getId(),
                            product.getName(),
                            product.getCategory(),
                            product.getStockQuantity(),
                            status
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Get low stock products
     */
    @Cacheable(value = "lowStock", key = "#threshold")
    public List<InventoryDto> getLowStockProducts(int threshold) {
        log.info("Fetching low stock products with threshold {}", threshold);

        List<Product> products = productRepository.findLowStockProducts(threshold);

        return products.stream()
                .map(product -> new InventoryDto(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getStockQuantity(),
                        "LOW"
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get user activity summary for a given date range
     */
    @Cacheable(value = "activitySummary", key = "#startDate + '-' + #endDate")
    public List<ActivitySummaryDto> getActivitySummary(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching activity summary from {} to {}", startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<Object[]> results = userActivityRepository.getActivityCountByType(start, end);

        Long total = results.stream()
                .mapToLong(r -> ((Number) r[1]).longValue())
                .sum();

        return results.stream()
                .map(result -> new ActivitySummaryDto(
                        (String) result[0],
                        ((Number) result[1]).longValue(),
                        total > 0 ? (((Number) result[1]).doubleValue() / total) * 100 : 0.0
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get most viewed products
     */
    @Cacheable(value = "mostViewed", key = "#startDate + '-' + #endDate + '-' + #limit")
    public List<TopProductDto> getMostViewedProducts(LocalDate startDate, LocalDate endDate, int limit) {
        log.info("Fetching most viewed products from {} to {}", startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<Object[]> results = userActivityRepository.getMostViewedProducts(start, end);

        Long total = results.stream()
                .mapToLong(r -> ((Number) r[1]).longValue())
                .sum();

        return results.stream()
                .limit(limit)
                .map(result -> new TopProductDto(
                        (String) result[0],
                        ((Number) result[1]).longValue(),
                        total > 0 ? (((Number) result[1]).doubleValue() / total) * 100 : 0.0
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get unique users count
     */
    @Cacheable(value = "uniqueUsers", key = "#startDate + '-' + #endDate")
    public Long getUniqueUsersCount(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching unique users count from {} to {}", startDate, endDate);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        return userActivityRepository.getUniqueUsersCount(start, end);
    }

    private String determineStockStatus(Integer quantity) {
        if (quantity < 20) return "LOW";
        if (quantity < 50) return "MEDIUM";
        return "ADEQUATE";
    }
}
