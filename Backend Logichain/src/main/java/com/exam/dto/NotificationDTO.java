package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    private Long userId;
    private com.exam.entities.NotificationType notificationType;
    private com.exam.entities.NotificationChannel channel;
    private String message;
    private com.exam.entities.NotificationStatus status;
}
