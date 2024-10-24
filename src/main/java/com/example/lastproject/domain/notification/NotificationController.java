package com.example.lastproject.domain.notification;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.notification.dto.NotificationRequest;
import com.example.lastproject.domain.notification.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 알림 생성
    @PostMapping("/notifications")
    public void sendNotification(@AuthenticationPrincipal AuthUser authUser, @RequestBody NotificationRequest notificationRequest) {
        notificationService.sendNotification(authUser, notificationRequest.getMessage());
    }

    // 알림 스트리밍
    @GetMapping("/notifications/stream")
    public SseEmitter streamNotifications(@AuthenticationPrincipal AuthUser authUser) {
        return notificationService.createEmitter(authUser.getUserId());
    }

    // 최신순 알림 목록 조회
    @GetMapping("/notifications/{receiverId}")
    public List<NotificationResponse> getNotifications(@PathVariable Long receiverId) {
        return notificationService.getNotifications(receiverId);
    }
}