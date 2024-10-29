package com.example.lastproject.aop;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.chat.dto.ChatRoomResponse;
import com.example.lastproject.domain.notification.service.NotificationService;
import com.example.lastproject.domain.party.dto.response.PartyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class NotificationAop {

    private final NotificationService notificationService;

    @Around("@annotation(com.example.lastproject.common.annotation.LogisticsNotify)")
    public Object handleNotification(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        // AuthUser 가져오기
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authUser != null) {
            // 결과 객체 타입에 따라 알림 로직 분기
            if (result instanceof PartyResponse) {
                PartyResponse partyResponse = (PartyResponse) result;
                notificationService.notifyUsersAboutPartyCreation(authUser, partyResponse);
                log.info("Party 생성 알림 전송 완료: {}", partyResponse);
            } else if (result instanceof ChatRoomResponse) {
                ChatRoomResponse chatRoomResponse = (ChatRoomResponse) result;
                notificationService.notifyUsersAboutPartyChatCreation(authUser, chatRoomResponse);
                log.info("ChatRoom 생성 알림 전송 완료: {}", chatRoomResponse);
            }
        } else {
            log.warn("알림 전송 실패: 유효한 AuthUser 객체를 찾을 수 없습니다.");
        }
        return result;
    }

}
