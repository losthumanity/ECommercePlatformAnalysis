package com.ecommerce.analytics.controller;

import com.ecommerce.analytics.dto.InventoryDto;
import com.ecommerce.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryAnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * GET /api/analytics/inventory/status
     */
    @GetMapping("/status")
    public ResponseEntity<List<InventoryDto>> getInventoryStatus() {
        log.info("Request received: Get inventory status");
        List<InventoryDto> result = analyticsService.getInventoryStatus();
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/analytics/inventory/low-stock?threshold=50
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryDto>> getLowStockProducts(
            @RequestParam(defaultValue = "50") int threshold) {

        log.info("Request received: Get low stock products with threshold {}", threshold);
        List<InventoryDto> result = analyticsService.getLowStockProducts(threshold);
        return ResponseEntity.ok(result);
    }
}
