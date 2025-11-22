package com.ecommerce.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySalesDto implements Serializable {
    private String category;
    private BigDecimal totalSales;
    private Long productCount;
}
