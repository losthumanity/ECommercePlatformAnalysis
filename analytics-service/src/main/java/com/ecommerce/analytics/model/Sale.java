package com.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales", indexes = {
    @Index(name = "idx_sale_date", columnList = "sale_date"),
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_customer_id", columnList = "customer_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "sale_date")
    private LocalDateTime saleDate;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(length = 50)
    private String status = "COMPLETED";

    @PrePersist
    protected void onCreate() {
        if (saleDate == null) {
            saleDate = LocalDateTime.now();
        }
    }
}
