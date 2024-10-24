package com.bookingprojectn1.repository;


import com.bookingprojectn1.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Integer countAllByUserIdAndReadFalse(Long userId);

    List<Notification> findAllByUserId(Long userId);
}


