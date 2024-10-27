package com.example.lastproject.domain.notification.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.notification.dto.request.NotificationRequest;
import com.example.lastproject.domain.notification.dto.response.NotificationListResponseDto;
import com.example.lastproject.domain.notification.dto.response.NotificationResponse;
import com.example.lastproject.domain.notification.entity.Notification;
import com.example.lastproject.domain.notification.entity.NotificationType;
import com.example.lastproject.domain.notification.repository.EmitterRepository;
import com.example.lastproject.domain.notification.repository.NotificationRepository;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    // 연결 지속시간 한시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private static final String CLIENT_BASIC_URL = "http://localhost:8080";

    // subscribe 로 연결 요청 시 SseEmitter(발신기)를 생성합니다.
    @Transactional
    public SseEmitter subscribe(AuthUser authUser, String lastEventId) {
        String emitterId = makeTimeIncludeId(authUser);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 시간 초과나 비동기 요청이 안되면 자동으로 삭제
        // SseEmitter 의 완료/시간초과/에러로 인한 전송 불가 시 SseEmitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 최초 연결시 더미데이터가 없으면 503 오류가 발생하기 때문에 해당 더미 데이터 생성
        String eventId = makeTimeIncludeId(authUser);
        sendToClient(emitter, emitterId, eventId, "연결되었습니다. EventStream Created. [userId=" + authUser.getUserId() + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        // 클라이언트의 요청 헤더에 lastEventId 값이 있는 경우 lastEventId 보다 더 큰(더 나중에 생성된) emitter를 찾아서 발송
        // lastEventId 있다는 것은 연결이 종료된 것. 해당 데이터가 남아있는지 살펴보고 있다면 남은 데이터 전송
        //  이 메서드는 이벤트를 보낼 때 하나는 클라이언트에서 확인할 이벤트 ID로 사용하고,
        //  다른 하나는 서버에서 이벤트를 추적하기 위한 식별 ID로 사용하려는 의도로 보입니다.
        //  하지만 만약 entry.getKey()를 두 번 사용할 필요가 없다면 코드에서 하나는 제거할 수 있습니다.
        if (!lastEventId.isEmpty()) {  // lastEventId가 존재한다는 것은 받지 못한 데이터가 있다는 것이다.(프론트에서 알아서 보내준다.)
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(authUser.getUserId()));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    // 데이터 유실 시점 파악 위함
    private String makeTimeIncludeId(AuthUser authUser) {
        return authUser.getUserId() + "_" + System.currentTimeMillis();
    }

    // 특정 SseEmitter 를 이용해 알림을 보냅니다. SseEmitter 는 최초 연결 시 생성되며,
    // 해당 SseEmitter 를 생성한 클라이언트로 알림을 발송하게 됩니다.
    public void sendToClient(SseEmitter emitter, String emitterId, String eventId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .name("SSE")
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
        }
    }

    // 알림 보내기 ( notificationRepository 에 저장 후 보내기)
    @Override
    public void send(AuthUser authUser, NotificationRequest request) {
        sendNotification(authUser, saveNotification(authUser, request));
    }

    // 알림 저장
    @Transactional
    public Notification saveNotification(AuthUser authUser, NotificationRequest request) {
        User user = User.fromAuthUser(authUser);

        // 알림 객체 생성
        Notification notification = Notification.builder()
                .receiver(user)
                .notificationType(request.getNotificationType())
                .content(request.getContent())
                .url(request.getUrl())
                .isRead(false)
                .build();
        notificationRepository.save(notification);
        return notification;
    }

    // 알림 보내기
    @Async
    public void sendNotification(AuthUser authUser, Notification notification) {
        String receiverId = String.valueOf(authUser.getUserId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        // 유저의 모든 SseEmitter 가져옴
        Map<String, SseEmitter> emitters = emitterRepository
                .findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장 (유실된 데이터 처리 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, eventId, NotificationResponse.of(notification));
                }
        );
    }

    // 찜한 품목의 파티가 생성된 경우 알림
    @Override
    @Transactional
    public void notifyUsersAboutPartyCreation(AuthUser authUser, Party party) {
        User receiver = User.fromAuthUser(authUser);
        String content = "찜하신 품목("+ party.getItem().getProductName() + ")의 파티가 생성되었습니다.";

        String redirectUrl = CLIENT_BASIC_URL + "/parties/" + party.getId();

        NotificationRequest request = NotificationRequest.builder()
                .notificationType(NotificationType.PARTY_CREATE)
                .content(content)
                .url(redirectUrl)
                .receiver(receiver)
                .build();

        send(authUser, request);
    }

    // 찜한 품목의 파티가 취소된 경우 알림
    @Override
    @Transactional
    public void notifyUsersAboutPartyCancellation(AuthUser authUser, Party party) {
        User receiver = User.fromAuthUser(authUser);
        String content = "찜하신 품목("+ party.getItem().getProductName() + ")의 파티가 취소되었습니다.";

        String redirectUrl = CLIENT_BASIC_URL + "/parties";

        NotificationRequest request = NotificationRequest.builder()
                .notificationType(NotificationType.PARTY_CREATE)
                .content(content)
                .url(redirectUrl)
                .receiver(receiver)
                .build();

        send(authUser, request);
    }

    // 참가 신청한 파티의 채팅창이 생성된 경우 알림
    @Override
    public void notifyUsersAboutPartyChatCreation(AuthUser authUser, Party party) {

    }

    // 알림 목록 조회
    @Override
    public NotificationListResponseDto getNotifications(AuthUser authUser) {
        return NotificationListResponseDto.of(
                notificationRepository.findAllByReceiverIdOrderByCreatedAtDesc(authUser.getUserId()));
    }

    // 알림 읽음 처리
    @Override
    @Transactional
    public void readNotification(Long notificationId, AuthUser authUser) {
        Notification notification = findNotification(notificationId);
        notification.read();
        notificationRepository.save(notification);
    }

    // 알림 삭제
    @Override
    @Transactional
    public void deleteNotification(Long notificationId, AuthUser authUser) {
        notificationRepository.delete(findNotification(notificationId));
    }

    @Override
    public Notification findNotification(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION));
    }
}
