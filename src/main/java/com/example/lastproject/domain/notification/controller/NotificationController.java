package com.example.lastproject.domain.notification.controller;


import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.notification.dto.response.NotificationListResponseDto;
import com.example.lastproject.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * SSE 연결
     * @param authUser
     * @param lastEventId
     * @return 클라이언트와 연결을 담당하는 SseEmitter 객체 반환
     */
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SseEmitter> subscribe(@AuthenticationPrincipal AuthUser authUser,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return ResponseEntity.ok(notificationService.subscribe(authUser, lastEventId));
    }

    /**
     * 알림 목록 조회
     * @param authUser
     * @return 알림 리스트 반환
     */
    @GetMapping
    public ResponseEntity<NotificationListResponseDto> getNotifications(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(notificationService.getNotifications(authUser));
    }

    /**
     * 알림 읽음으로 변경
     * @param notificationId
     * @param authUser
     * @return 성공 메시지
     */
    @PatchMapping(value = "/{notificationId}")
    public ResponseEntity<String> readNotification(@PathVariable Long notificationId,
                                                        @AuthenticationPrincipal AuthUser authUser) {
        notificationService.readNotification(notificationId, authUser);
        return ResponseEntity.ok("알림이 읽음으로 변경되었습니다.");
    }

    /**
     * 알림 삭제
     * @param notificationId
     * @param authUser
     * @return 성공 메시지
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId,
                                                                   @AuthenticationPrincipal AuthUser authUser) {
        notificationService.deleteNotification(notificationId, authUser);
        return ResponseEntity.ok("알림이 성공적으로 삭제되었습니다.");
    }
}
