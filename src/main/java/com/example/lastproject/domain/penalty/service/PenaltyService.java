package com.example.lastproject.domain.penalty.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.entity.Penalty;
import com.example.lastproject.domain.penalty.repository.PenaltyRepository;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.repository.UserRepository;
import com.example.lastproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository ;

    public void sendPenalty(Long partyId, PenaltyRequest request) {

        User findUser = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")
        );

        Party party = partyRepository.findByPartyId(partyId).orElseThrow(
                () -> new CustomException(ErrorCode.PARTY_NOT_FOUND, "파티를 찾을 수 없습니다.")
        );

        Penalty penalty = new Penalty(
                party,
                findUser
        );

        Penalty newPenalty = penaltyRepository.save(penalty);
    }
}
