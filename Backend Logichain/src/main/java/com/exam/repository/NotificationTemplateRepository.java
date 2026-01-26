package com.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.NotificationTemplate;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
}