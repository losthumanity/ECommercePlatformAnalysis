package com.ecommerce.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto implements Serializable {
    private Long productId;
    private String productName;
    private String category;
    private Integer stockQuantity;
    private String status; // LOW, MEDIUM, ADEQUATE
}
