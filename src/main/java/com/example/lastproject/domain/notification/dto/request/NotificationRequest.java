package com.example.lastproject.domain.notification.dto.request;

import com.example.lastproject.domain.notification.entity.NotificationType;
import com.example.lastproject.domain.user.entity.User;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRequest {

    private String url;
    private String content;
    private User receiver;
    private NotificationType notificationType;
}
