package com.ecommerce.analytics;

import com.ecommerce.analytics.dto.*;
import com.ecommerce.analytics.model.Product;
import com.ecommerce.analytics.repository.ProductRepository;
import com.ecommerce.analytics.repository.SaleRepository;
import com.ecommerce.analytics.repository.UserActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserActivityRepository userActivityRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 12, 31);
    }

    @Test
    void testGetSalesByCategory_ReturnsCorrectData() {
        // Arrange
        Object[] result1 = new Object[]{"Electronics", new BigDecimal("5000.00")};
        Object[] result2 = new Object[]{"Furniture", new BigDecimal("3000.00")};
        List<Object[]> mockResults = Arrays.asList(result1, result2);

        when(saleRepository.getSalesByCategory(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockResults);

        // Act
        List<CategorySalesDto> results = analyticsService.getSalesByCategory(startDate, endDate);

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getCategory()).isEqualTo("Electronics");
        assertThat(results.get(0).getTotalSales()).isEqualTo(new BigDecimal("5000.00"));
        assertThat(results.get(1).getCategory()).isEqualTo("Furniture");

        verify(saleRepository, times(1)).getSalesByCategory(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetTopSellingProducts_ReturnsLimitedResults() {
        // Arrange
        Object[] result1 = new Object[]{"Laptop", 100L};
        Object[] result2 = new Object[]{"Mouse", 50L};
        Object[] result3 = new Object[]{"Chair", 30L};
        List<Object[]> mockResults = Arrays.asList(result1, result2, result3);

        when(saleRepository.getTopSellingProducts(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockResults);

        // Act
        List<TopProductDto> results = analyticsService.getTopSellingProducts(startDate, endDate, 2);

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getProductName()).isEqualTo("Laptop");
        assertThat(results.get(0).getQuantitySold()).isEqualTo(100L);
        assertThat(results.get(0).getPercentageOfTotal()).isGreaterThan(0);

        verify(saleRepository, times(1)).getTopSellingProducts(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetTotalSales_ReturnsCorrectAmount() {
        // Arrange
        BigDecimal expectedTotal = new BigDecimal("15000.00");
        when(saleRepository.getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(expectedTotal);

        // Act
        BigDecimal result = analyticsService.getTotalSales(startDate, endDate);

        // Assert
        assertThat(result).isEqualTo(expectedTotal);
        verify(saleRepository, times(1)).getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetTotalSales_ReturnsZeroWhenNull() {
        // Arrange
        when(saleRepository.getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(null);

        // Act
        BigDecimal result = analyticsService.getTotalSales(startDate, endDate);

        // Assert
        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void testGetInventoryStatus_ReturnsAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setCategory("Electronics");
        product1.setStockQuantity(15);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Chair");
        product2.setCategory("Furniture");
        product2.setStockQuantity(100);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        List<InventoryDto> results = analyticsService.getInventoryStatus();

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getStatus()).isEqualTo("LOW");
        assertThat(results.get(1).getStatus()).isEqualTo("ADEQUATE");

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetLowStockProducts_ReturnsFilteredProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setCategory("Electronics");
        product1.setStockQuantity(15);

        when(productRepository.findLowStockProducts(50)).thenReturn(Arrays.asList(product1));

        // Act
        List<InventoryDto> results = analyticsService.getLowStockProducts(50);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProductName()).isEqualTo("Laptop");
        assertThat(results.get(0).getStatus()).isEqualTo("LOW");

        verify(productRepository, times(1)).findLowStockProducts(50);
    }

    @Test
    void testGetActivitySummary_ReturnsCorrectPercentages() {
        // Arrange
        Object[] result1 = new Object[]{"VIEW", 100L};
        Object[] result2 = new Object[]{"PURCHASE", 50L};
        List<Object[]> mockResults = Arrays.asList(result1, result2);

        when(userActivityRepository.getActivityCountByType(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockResults);

        // Act
        List<ActivitySummaryDto> results = analyticsService.getActivitySummary(startDate, endDate);

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getActivityType()).isEqualTo("VIEW");
        assertThat(results.get(0).getCount()).isEqualTo(100L);
        assertThat(results.get(0).getPercentage()).isCloseTo(66.67, within(0.1));

        verify(userActivityRepository, times(1))
                .getActivityCountByType(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetUniqueUsersCount_ReturnsCount() {
        // Arrange
        when(userActivityRepository.getUniqueUsersCount(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(250L);

        // Act
        Long result = analyticsService.getUniqueUsersCount(startDate, endDate);

        // Assert
        assertThat(result).isEqualTo(250L);
        verify(userActivityRepository, times(1))
                .getUniqueUsersCount(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testGetMostViewedProducts_ReturnsLimitedResults() {
        // Arrange
        Object[] result1 = new Object[]{"Laptop", 500L};
        Object[] result2 = new Object[]{"Mouse", 300L};
        List<Object[]> mockResults = Arrays.asList(result1, result2);

        when(userActivityRepository.getMostViewedProducts(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockResults);

        // Act
        List<TopProductDto> results = analyticsService.getMostViewedProducts(startDate, endDate, 5);

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getProductName()).isEqualTo("Laptop");
        assertThat(results.get(0).getQuantitySold()).isEqualTo(500L);

        verify(userActivityRepository, times(1))
                .getMostViewedProducts(any(LocalDateTime.class), any(LocalDateTime.class));
    }
}
