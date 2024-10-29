package com.example.lastproject.aop;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.notification.service.NotificationService;
import com.example.lastproject.domain.party.entity.Party;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class NotificationAop {

    private final NotificationService notificationService;

    @AfterReturning(
            pointcut = "@annotation(com.example.lastproject.common.annotation.LogisticsNotify)",
            returning = "party")
    public void afterPartyCreation(JoinPoint joinPoint, Object party) {
        // JoinPoint를 사용하여 AuthUser 파라미터 추출
        AuthUser authUser = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof AuthUser) {
                authUser = (AuthUser) arg;
                break;
            }
        }

        if (authUser != null && party instanceof Party) {
            // 파티 생성 후 알림 로직 호출
            notificationService.notifyUsersAboutPartyCreation(authUser, (Party) party);
        } else {
            log.warn("알림 전송 실패: 유효한 AuthUser 또는 Party 객체를 찾을 수 없습니다.");
        }
    }

}
