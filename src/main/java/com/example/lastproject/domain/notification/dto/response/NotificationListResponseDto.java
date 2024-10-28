package com.example.lastproject.domain.notification.dto.response;

import com.example.lastproject.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NotificationListResponseDto {

    private final List<NotificationResponse> notifications;

    public static NotificationListResponseDto of(List<Notification> notifications) {
        return NotificationListResponseDto.builder()
                .notifications(notifications.stream().map(NotificationResponse::of)
                        .toList())
                .build();
    }

}
