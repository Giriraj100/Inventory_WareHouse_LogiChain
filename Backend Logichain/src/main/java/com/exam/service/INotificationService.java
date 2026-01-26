package com.exam.service;



import java.util.List;

import com.exam.dto.NotificationDTO;
import com.exam.entities.Notification;

public interface INotificationService {

	List<NotificationDTO> getAll();
//    List<NotificationDTO> getAll();
//    NotificationDTO getById(Long id);
//    List<NotificationDTO> getByUserId(Long userId);
//    NotificationDTO save(Notification notification);
//    void delete(Long id);

	NotificationDTO getById(Long id);

	List<NotificationDTO> getByUserId(Long userId);

	NotificationDTO save(Notification notification);

	void delete(Long id);
}