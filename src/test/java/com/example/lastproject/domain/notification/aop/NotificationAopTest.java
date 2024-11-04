package com.example.lastproject.domain.notification.aop;

import com.example.lastproject.aop.NotificationAop;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.chat.dto.ChatRoomResponse;
import com.example.lastproject.domain.notification.service.NotificationService;
import com.example.lastproject.domain.party.dto.response.PartyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationAopTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationAop notificationAop;

    private AuthUser authUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authUser = mock(AuthUser.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(authUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testAfterPartyCreation() {
        // Given
        PartyResponse partyResponse = mock(PartyResponse.class);
        when(partyResponse.getCategory()).thenReturn("PartyCategory");
        when(partyResponse.getId()).thenReturn(1L);

        // When
        notificationAop.afterPartyCreation(partyResponse);

        // Then
        verify(notificationService).notifyUsersAboutPartyCreation(eq(authUser), eq("PartyCategory"), eq(1L));
    }

    @Test
    public void testAfterPartyCancellation() {
        // When
        notificationAop.afterPartyCancellation();

        // Then
        verify(notificationService).notifyUsersAboutPartyCancellation(eq(authUser));
    }

    @Test
    public void testAfterPartyCreationWithValidAuthUser() {
        // Given
        PartyResponse partyResponse = mock(PartyResponse.class);
        when(partyResponse.getCategory()).thenReturn("PartyCategory");
        when(partyResponse.getId()).thenReturn(1L);

        // When
        notificationAop.afterPartyCreation(partyResponse);

        // Then
        verify(notificationService, times(1)).notifyUsersAboutPartyCreation(eq(authUser), eq("PartyCategory"), eq(1L));
    }

    @Test
    public void testAfterPartyCancellationWithValidAuthUser() {
        // When
        notificationAop.afterPartyCancellation();

        // Then
        verify(notificationService, times(1)).notifyUsersAboutPartyCancellation(eq(authUser));
    }

}
