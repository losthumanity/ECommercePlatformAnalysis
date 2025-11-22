package com.ecommerce.analytics.controller;

import com.ecommerce.analytics.dto.*;
import com.ecommerce.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/sales")
@RequiredArgsConstructor
@Slf4j
public class SalesAnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * GET /api/analytics/sales/by-category?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/by-category")
    public ResponseEntity<List<CategorySalesDto>> getSalesByCategory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Request received: Get sales by category from {} to {}", startDate, endDate);
        List<CategorySalesDto> result = analyticsService.getSalesByCategory(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/analytics/sales/top-products?startDate=2024-01-01&endDate=2024-12-31&limit=10
     */
    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDto>> getTopSellingProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "10") int limit) {

        log.info("Request received: Get top {} selling products from {} to {}", limit, startDate, endDate);
        List<TopProductDto> result = analyticsService.getTopSellingProducts(startDate, endDate, limit);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/analytics/sales/daily?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/daily")
    public ResponseEntity<List<DailySalesDto>> getDailySales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Request received: Get daily sales from {} to {}", startDate, endDate);
        List<DailySalesDto> result = analyticsService.getDailySales(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/analytics/sales/total?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalSales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Request received: Get total sales from {} to {}", startDate, endDate);
        BigDecimal result = analyticsService.getTotalSales(startDate, endDate);
        return ResponseEntity.ok(result);
    }
}
