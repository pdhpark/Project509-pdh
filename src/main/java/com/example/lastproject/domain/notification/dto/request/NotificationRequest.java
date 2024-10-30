package com.example.lastproject.domain.notification.dto.request;

import com.example.lastproject.domain.notification.entity.NotificationType;
import com.example.lastproject.domain.user.entity.User;
import lombok.*;

@Getter
@Builder
public class NotificationRequest {

    private String url;
    private String content;
    private NotificationType notificationType;
    private User receiver;

}
