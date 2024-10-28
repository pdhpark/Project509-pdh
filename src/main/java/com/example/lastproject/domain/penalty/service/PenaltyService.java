package com.example.lastproject.domain.penalty.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.repository.PartyRepository;
import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.entity.Penalty;
import com.example.lastproject.domain.penalty.repository.PenaltyRepository;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    /**
     * 페널티 부여
     *
     * @param partyId 페널티를 부여할 유저가 속했던 파이
     * @param request 페널티를 부여할 유저의 리스트
     */
    @Transactional
    public void sendPenalty(Long partyId, PenaltyRequest request) {

        // requestDTO 에서 유저 리스트 받아오기
        List<Long> userIds = request.getUserIds();

        // 유저 리스트에 있는 모든 유저를 레포지토리에서 찾아오기
        List<User> users = userRepository.findAllById(userIds);

        Party party = partyRepository.findById(partyId).orElseThrow(
                () -> new CustomException(ErrorCode.PARTY_NOT_FOUND)
        );

        List<Penalty> penalties = new ArrayList<>();

        // 지정된 유저를 대상으로 각각 페널티를 부여하고 리스트에 추가
        for (User user : users) {
            Penalty penalty = new Penalty(party, user);
            penalties.add(penalty);
        }

        // 추가한 페널티 리스트를 한번에 save
        penaltyRepository.saveAll(penalties);

    }

}
