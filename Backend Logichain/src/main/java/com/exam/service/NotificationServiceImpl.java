package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.custom_exceptions.ResourceNotFoundException;
import com.exam.dto.NotificationDTO;
import com.exam.entities.Notification;
import com.exam.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final NotificationRepository repo;
    private final ModelMapper mapper;

    @Override
    public List<NotificationDTO> getAll() {
        return repo.findAll().stream()
                .map(e -> mapper.map(e, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getById(Long id) {
        Notification notification = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return mapper.map(notification, NotificationDTO.class);
    }

    @Override
    public List<NotificationDTO> getByUserId(Long userId) {
        List<Notification> notifications = repo.findByUserId(userId);
        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("No notifications found for user id: " + userId);
        }
        return notifications.stream()
                .map(e -> mapper.map(e, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO save(Notification notification) {
        return mapper.map(repo.save(notification), NotificationDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        repo.deleteById(id);
    }
}