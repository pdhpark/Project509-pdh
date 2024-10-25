package com.example.lastproject.domain.penalty.service;

import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.repository.PartyRepository;
import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.entity.Penalty;
import com.example.lastproject.domain.penalty.repository.PenaltyRepository;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.enums.UserRole;
import com.example.lastproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PenaltyServiceTest {

    // 테스트할 모킹 레포지토리 객체
    @Mock
    private PenaltyRepository penaltyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PartyRepository partyRepository;

    // 서비스 로직 테스트 객체
    @InjectMocks
    private PenaltyService penaltyService;

    /**
     * 페널티 부여에 성공한 경우
     */
    @Nested
    class SendPenaltySuccess {

        @Test
        void 페널티부여_성공하기() {

            // given
            Long partyId = 1L;
            List<Long> userIds = new ArrayList<>(List.of(1L, 2L));
            User user1 = new User(
                    "1@1",
                    "123456!A",
                    "수달조아",
                    "사하구 사하로",
                    UserRole.ROLE_ADMIN
            );

            User user2 = new User(
                    "2@1",
                    "123456!A",
                    "강쥐조아",
                    "강남구 청담동",
                    UserRole.ROLE_ADMIN
            );

            when(userRepository.findAllById(userIds))
                    .thenReturn(new ArrayList<>(List.of(user1, user2)));
            when(partyRepository.findById(partyId))
                    .thenReturn(Optional.of(new Party()));

            // when
            penaltyService.sendPenalty(partyId, new PenaltyRequest(userIds));

            // then
            verify(penaltyRepository, times(2)).save(any(Penalty.class));
        }
    }

    /**
     * 페널티 부여에 실패한 경우
     */
    @Nested
    class SendPenaltyFailure {

        @Test
        void 페널티부여_실패하기() {

        }
    }

}