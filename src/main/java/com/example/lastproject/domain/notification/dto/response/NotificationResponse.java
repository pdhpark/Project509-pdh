package com.example.lastproject.domain.notification.dto.response;

import com.example.lastproject.domain.notification.entity.Notification;
import com.example.lastproject.domain.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long id;
    private String content;
    private NotificationType type;
    private String url;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public static NotificationResponse of(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .content(notification.getContent())
                .type(notification.getNotificationType())
                .url(notification.getUrl())
                .isRead(false)
                .build();
    }
}
