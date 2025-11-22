package com.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activities", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_activity_type", columnList = "activity_type"),
    @Index(name = "idx_activity_timestamp", columnList = "activity_timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "activity_type", nullable = false, length = 50)
    private String activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "activity_timestamp")
    private LocalDateTime activityTimestamp;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @PrePersist
    protected void onCreate() {
        if (activityTimestamp == null) {
            activityTimestamp = LocalDateTime.now();
        }
    }
}
