package com.example.lastproject.domain.notification.service;

import com.example.lastproject.domain.notification.repository.EmitterRepositoryImpl;
import com.example.lastproject.domain.notification.repository.NotificationRepository;
import com.example.lastproject.domain.user.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

//@ExtendWith(MockitoExtension.class)
//public class NotificationServiceTest {
//
//    @InjectMocks
//    private NotificationServiceImpl notificationService;
//
//    @Mock
//    private EmitterRepositoryImpl emitterRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    public void 찜한품목이_아니면_예외가_발생한다() {
//        // given
//
//
//
//        //when
//
//
//
//        //then
//
//
//    }
//
//
//}
