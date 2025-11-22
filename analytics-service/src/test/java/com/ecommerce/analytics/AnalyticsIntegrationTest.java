package com.ecommerce.analytics.integration;

import com.ecommerce.analytics.dto.CategorySalesDto;
import com.ecommerce.analytics.dto.InventoryDto;
import com.ecommerce.analytics.model.Product;
import com.ecommerce.analytics.model.Sale;
import com.ecommerce.analytics.repository.ProductRepository;
import com.ecommerce.analytics.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AnalyticsIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.cache.type", () -> "none");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
        productRepository.deleteAll();

        // Create test products
        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setCategory("Electronics");
        laptop.setPrice(new BigDecimal("1299.99"));
        laptop.setStockQuantity(50);
        laptop = productRepository.save(laptop);

        Product chair = new Product();
        chair.setName("Office Chair");
        chair.setCategory("Furniture");
        chair.setPrice(new BigDecimal("249.99"));
        chair.setStockQuantity(15);
        chair = productRepository.save(chair);

        // Create test sales
        Sale sale1 = new Sale();
        sale1.setProduct(laptop);
        sale1.setQuantity(2);
        sale1.setTotalAmount(new BigDecimal("2599.98"));
        sale1.setSaleDate(LocalDateTime.now().minusDays(5));
        sale1.setStatus("COMPLETED");
        saleRepository.save(sale1);

        Sale sale2 = new Sale();
        sale2.setProduct(chair);
        sale2.setQuantity(1);
        sale2.setTotalAmount(new BigDecimal("249.99"));
        sale2.setSaleDate(LocalDateTime.now().minusDays(3));
        sale2.setStatus("COMPLETED");
        saleRepository.save(sale2);
    }

    @Test
    void testGetSalesByCategory_ReturnsCorrectData() throws Exception {
        mockMvc.perform(get("/api/analytics/sales/by-category")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[*].category", hasItems("Electronics", "Furniture")));
    }

    @Test
    void testGetTopSellingProducts_ReturnsLimitedResults() throws Exception {
        mockMvc.perform(get("/api/analytics/sales/top-products")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(5))))
                .andExpect(jsonPath("$[0].productName", notNullValue()))
                .andExpect(jsonPath("$[0].quantitySold", greaterThan(0)));
    }

    @Test
    void testGetTotalSales_ReturnsValidAmount() throws Exception {
        mockMvc.perform(get("/api/analytics/sales/total")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", greaterThan(0)));
    }

    @Test
    void testGetInventoryStatus_ReturnsAllProducts() throws Exception {
        mockMvc.perform(get("/api/analytics/inventory/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].productName", hasItems("Laptop", "Office Chair")))
                .andExpect(jsonPath("$[*].status", everyItem(notNullValue())));
    }

    @Test
    void testGetLowStockProducts_ReturnsFilteredResults() throws Exception {
        mockMvc.perform(get("/api/analytics/inventory/low-stock")
                        .param("threshold", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].stockQuantity", everyItem(lessThanOrEqualTo(50))));
    }

    @Test
    void testGetDailySales_ReturnsTimeSeriesData() throws Exception {
        mockMvc.perform(get("/api/analytics/sales/daily")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(java.util.List.class)));
    }
}
