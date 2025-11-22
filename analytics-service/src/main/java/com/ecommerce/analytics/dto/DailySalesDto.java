package com.ecommerce.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySalesDto implements Serializable {
    private LocalDate date;
    private BigDecimal totalSales;
    private Long transactionCount;
}
