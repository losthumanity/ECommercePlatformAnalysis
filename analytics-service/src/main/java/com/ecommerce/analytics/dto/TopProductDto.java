package com.ecommerce.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopProductDto implements Serializable {
    private String productName;
    private Long quantitySold;
    private Double percentageOfTotal;
}
