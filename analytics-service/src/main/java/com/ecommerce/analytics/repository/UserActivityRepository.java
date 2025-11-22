package com.ecommerce.analytics.repository;

import com.ecommerce.analytics.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findByUserId(Long userId);

    List<UserActivity> findByActivityType(String activityType);

    @Query("SELECT ua.activityType, COUNT(ua) FROM UserActivity ua " +
           "WHERE ua.activityTimestamp BETWEEN :start AND :end GROUP BY ua.activityType")
    List<Object[]> getActivityCountByType(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT p.name, COUNT(ua) as viewCount FROM UserActivity ua JOIN ua.product p " +
           "WHERE ua.activityType = 'VIEW' AND ua.activityTimestamp BETWEEN :start AND :end " +
           "GROUP BY p.id, p.name ORDER BY viewCount DESC")
    List<Object[]> getMostViewedProducts(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT ua.userId) FROM UserActivity ua " +
           "WHERE ua.activityTimestamp BETWEEN :start AND :end")
    Long getUniqueUsersCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
