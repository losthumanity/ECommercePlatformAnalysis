package com.ecommerce.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySummaryDto implements Serializable {
    private String activityType;
    private Long count;
    private Double percentage;
}
