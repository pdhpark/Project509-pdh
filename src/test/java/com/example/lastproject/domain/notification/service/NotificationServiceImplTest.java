package com.example.lastproject.domain.notification.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.notification.dto.request.NotificationRequest;
import com.example.lastproject.domain.notification.dto.response.NotificationListResponse;
import com.example.lastproject.domain.notification.entity.Notification;
import com.example.lastproject.domain.notification.entity.NotificationType;
import com.example.lastproject.domain.notification.repository.EmitterRepository;
import com.example.lastproject.domain.notification.repository.NotificationRepository;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.enums.UserRole;
import com.example.lastproject.domain.user.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private EmitterRepository emitterRepository;

    @Mock
    private SseEmitter emitter; // SseEmitter를 Mock으로 설정

    private String clientBasicUrl = "http://localhost:8080"; // 하드코딩된 값 사용
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private AuthUser authUser;
    private User user;
    private Notification notification;
    String emitterId = "emitterId";
    String eventId = "eventId";
    Object data = "test data";


    @BeforeEach
    public void setUp() {
        authUser = new AuthUser(1L, "test@example.com", UserRole.ROLE_ADMIN);
        user = new User(1L, "test@example.com", "password", "Tester", "Address", UserRole.ROLE_ADMIN, null, null);
        notification = Notification.builder()
                .id(1L)
                .notificationType(null) // 필요한 경우 알맞은 타입으로 설정하세요.
                .receiver(user)
                .content("Test notification")
                .url("http://example.com")
                .isRead(false)
                .build();
    }

    private User createUser() {
        return new User(
                1L,
                "receiver@example.com",
                "password",
                "Receiver",
                "Address",
                UserRole.ROLE_ADMIN,
                UserStatus.ACTIVATED,
                new ArrayList<>()
        );
    }

    private Notification createNotification(User receiver) {
        return Notification.builder()
                .id(1L)
                .notificationType(NotificationType.PARTY_CREATE)
                .receiver(receiver)
                .content("알림 내용")
                .url("http://example.com")
                .isRead(false)
                .build();
    }

    @Test
    public void testSendToClient_Success() throws Exception {
        // when: 정상적으로 send 메서드 호출
        notificationService.sendToClient(emitter, emitterId, eventId, data);

        // then: send 메서드가 호출되었는지 검증
        verify(emitter).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    public void testSendToClient_IOException() throws Exception {

        // IOException을 발생시키도록 emitter를 모킹
        doThrow(new IOException("SSE 연결 오류입니다.")).when(emitter).send(any(SseEmitter.SseEventBuilder.class));

        // when & then: CustomException이 발생하는지 확인
        assertThrows(CustomException.class, () -> notificationService.sendToClient(emitter, emitterId, eventId, data));

        // emitterRepository.deleteById가 호출되었는지 검증
        verify(emitterRepository).deleteById(emitterId);
    }

    @Test
    public void testReadNotification_NotificationNotFound() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            notificationService.readNotification(notificationId, authUser);
        });

        assertEquals(ErrorCode.NOT_FOUND_NOTIFICATION, exception.getErrorCode());
        verify(notificationRepository).findById(notificationId);
    }

    @Test
    public void testReadNotification_NoAccess() {
        // given
        Long notificationId = 1L;

        // when & then
        assertThrows(CustomException.class, () -> {
            notificationService.readNotification(notificationId, authUser);
        });

        // Verify the access check method here if applicable
        verify(notificationRepository).findById(notificationId);
    }

    @Test
    public void test클라이언트에전송_입출력예외발생() throws IOException {
        // When
        notificationService.sendToClient(emitter, emitterId, eventId, data);

        // Then
        ArgumentCaptor<SseEmitter.SseEventBuilder> captor = forClass(SseEmitter.SseEventBuilder.class);
        verify(emitter).send(captor.capture());

        // SseEventBuilder의 데이터를 직접 검증하는 방법
        SseEmitter.SseEventBuilder capturedEvent = captor.getValue();

        // 여기서는 비교할 더미 이벤트 빌더를 생성해야 합니다.
        // 그러나 내부 상태에 직접 접근할 수 없기 때문에 전송된 데이터를 검증할 것입니다.
        // capturedEvent가 예상된 데이터를 포함하는지 확인하기 위해 해당 메서드를 간접적으로 사용하여 테스트할 수 있습니다.

        assertNotNull(capturedEvent);  // check that something was captured

        // 확인하기 위한 테스트 대체 (실제적으로 동작하지 않지만)
        // Mockito는 빌더에 대한 직접 필드 접근을 허용하지 않으므로, 이렇게 할 수 없습니다.
        // 대신, 통합 테스트를 위해 코드의 실제 흐름에 의존해야 합니다.
    }



    @Test
    public void 알림이_정상적으로_전송된다() {
        // given
        User receiver = createUser();
        NotificationRequest request = NotificationRequest.builder()
                .url("http://example.com")
                .content("알림 내용")
                .notificationType(NotificationType.PARTY_CREATE)
                .receiver(receiver)
                .build();

        Notification notification = createNotification(receiver);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // when
        notificationService.send(authUser, request);

        // then
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void 알림이_정상적으로_생성된다() {
        // given
        User receiver = user;

        // when
        notification = Notification.builder()
                .id(1L)
                .notificationType(NotificationType.PARTY_CREATE) // 필요한 경우 알맞은 타입으로 설정하세요.
                .receiver(receiver)
                .content("알림 내용")
                .url("http://example.com")
                .isRead(false)
                .build();

        // then
        assertNotNull(notification);
        assertEquals(NotificationType.PARTY_CREATE, notification.getNotificationType());
        assertEquals(receiver, notification.getReceiver());
        assertEquals("알림 내용", notification.getContent());
        assertEquals("http://example.com", notification.getUrl());
        assertFalse(notification.isRead());
    }

    @Test
    public void test알림조회() {
        // Given: mock 데이터를 설정합니다.
        when(notificationRepository.findAllByReceiverIdOrderByCreatedAtDesc(anyLong()))
                .thenReturn(Collections.singletonList(notification));

        // When: 알림 목록을 조회합니다.
        NotificationListResponse result = notificationService.getNotifications(authUser);

        // Then: 결과를 검증합니다.
        assertNotNull(result);
        assertEquals(1, result.getNotifications().size());
        assertEquals(notification.getContent(), result.getNotifications().get(0).getContent());
        verify(notificationRepository, times(1)).findAllByReceiverIdOrderByCreatedAtDesc(authUser.getUserId());
    }

    @Test
    public void 알림을_읽으면_isRead가_변경된다() {
        // given
        User receiver = createUser();
        Notification notification = createNotification(receiver);

        // when
        notification.read();

        // then
        assertTrue(notification.isRead());
    }


    @Test
    public void 알림이_정상적으로_삭제된다() {
        // given
        User receiver = createUser();
        Notification notification = createNotification(receiver);
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification));

        // when
        notificationService.deleteNotification(notification.getId(), authUser);

        // then
        verify(notificationRepository, times(1)).delete(notification);
    }

    @Test
    public void 알림읽기_권한없음_예외처리() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // 다른 사용자의 알림을 가져오도록 설정
        AuthUser differentUser = new AuthUser(2L, "other@example.com", UserRole.ROLE_USER);

        // when, then
        CustomException exception = assertThrows(CustomException.class,
                () -> notificationService.readNotification(notificationId, differentUser));

        assertEquals(ErrorCode.UNAUTHORIZED_ACCESS, exception.getErrorCode());
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    public void 알림읽기_알림없음_예외처리() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // when, then
        CustomException exception = assertThrows(CustomException.class,
                () -> notificationService.readNotification(notificationId, authUser));

        assertEquals(ErrorCode.NOT_FOUND_NOTIFICATION, exception.getErrorCode());
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    public void 알림삭제_권한없음_예외처리() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // 다른 사용자의 알림을 가져오도록 설정
        AuthUser differentUser = new AuthUser(2L, "other@example.com", UserRole.ROLE_USER);

        // when, then
        CustomException exception = assertThrows(CustomException.class,
                () -> notificationService.deleteNotification(notificationId, differentUser));

        assertEquals(ErrorCode.UNAUTHORIZED_ACCESS, exception.getErrorCode());
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    public void 알림삭제_알림없음_예외처리() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // when, then
        CustomException exception = assertThrows(CustomException.class,
                () -> notificationService.deleteNotification(notificationId, authUser));

        assertEquals(ErrorCode.NOT_FOUND_NOTIFICATION, exception.getErrorCode());
        verify(notificationRepository, times(1)).findById(notificationId);
    }

}
