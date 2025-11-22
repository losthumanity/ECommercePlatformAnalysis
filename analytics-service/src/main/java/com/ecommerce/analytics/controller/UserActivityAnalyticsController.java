package com.ecommerce.analytics.controller;

import com.ecommerce.analytics.dto.ActivitySummaryDto;
import com.ecommerce.analytics.dto.TopProductDto;
import com.ecommerce.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/user-activity")
@RequiredArgsConstructor
@Slf4j
public class UserActivityAnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * GET /api/analytics/user-activity/summary?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/summary")
    public ResponseEntity<List<ActivitySummaryDto>> getActivitySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Request received: Get activity summary from {} to {}", startDate, endDate);
        List<ActivitySummaryDto> result = analyticsService.getActivitySummary(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/analytics/user-activity/most-viewed?startDate=2024-01-01&endDate=2024-12-31&limit=10
     */
    @GetMapping("/most-viewed")
    public ResponseEntity<List<TopProductDto>> getMostViewedProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "10") int limit) {

        log.info("Request received: Get most viewed products from {} to {}", startDate, endDate);
        List<TopProductDto> result = analyticsService.getMostViewedProducts(startDate, endDate, limit);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/analytics/user-activity/unique-users?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/unique-users")
    public ResponseEntity<Long> getUniqueUsersCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Request received: Get unique users count from {} to {}", startDate, endDate);
        Long result = analyticsService.getUniqueUsersCount(startDate, endDate);
        return ResponseEntity.ok(result);
    }
}
