package com.example.lastproject.domain.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long notificationId;
    private String message;
    private LocalDateTime createdAt;
}
