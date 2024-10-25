package com.example.lastproject.domain.notification.service;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.notification.dto.response.NotificationResponse;
import com.example.lastproject.domain.notification.entity.Notification;
import com.example.lastproject.domain.notification.repository.NotificationRepository;
import com.example.lastproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(AuthUser authUser, String message) {
        User user = User.fromAuthUser(authUser);

        // User 객체를 사용해 Notification 생성
        Notification notification = new Notification(message, user);

        // Notification 저장
        Notification savedNotification = notificationRepository.save(notification);

        // 실시간 알림 전송
        sendRealTimeNotification(savedNotification);
    }

    private void sendRealTimeNotification(Notification notification) {
        SseEmitter emitter = userEmitters.get(notification.getReceiver().getId());
        if (emitter != null) {
            executor.execute(() -> {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(notification.getMessage()));
                } catch (Exception e) {
                    // 예외 처리: 클라이언트와의 연결이 끊겼을 경우
                    userEmitters.remove(notification.getReceiver().getId()); // 해당 사용자의 emitter 제거
                    // SLF4J 로깅 사용
                    log.error("Failed to send notification to user with ID: {}. Error: {}", notification.getReceiver().getId(), e.getMessage());
                    // 예외 스택 추적을 로그에 출력
                    log.error("Exception: ", e);
                }
            });
        }
    }

    @Transactional
    public SseEmitter createEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter();
        userEmitters.put(userId, emitter);
        return emitter;
    }

    // 최신순 알림 목록 조회
    public List<NotificationResponse> getNotifications(Long receiverId) {
        List<Notification> notifications = notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId);
        log.info("receiverId: {}", receiverId);

        return notifications.stream()
                .map(notification -> new NotificationResponse(notification.getId(), notification.getMessage(), notification.getCreatedAt()))
                .collect(Collectors.toList());  // 각 알림에 대해 ID와 메시지만 반환
    }
}