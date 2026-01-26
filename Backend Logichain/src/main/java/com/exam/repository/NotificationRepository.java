package com.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}