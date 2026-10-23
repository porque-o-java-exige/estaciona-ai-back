package com.estaciona_ai.notifications;

import com.estaciona_ai.users.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public void sendNotification(UserEntity user, String title, String message) {
        NotificationEntity notification = new NotificationEntity();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUserNotifications(UUID userId) {
        List<NotificationEntity> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notificationMapper.toResponseList(notifications);
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada com ID: " + notificationId));

        notification.setRead(true);
        notificationRepository.save(notification);
    }
}