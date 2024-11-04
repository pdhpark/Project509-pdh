package com.example.lastproject.domain.penalty.service;

import com.example.lastproject.common.exception.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.common.dto.AuthUser;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import com.example.lastproject.domain.party.repository.PartyRepository;
import com.example.lastproject.domain.partymember.entity.PartyMember;
import com.example.lastproject.domain.partymember.enums.PartyMemberRole;
import com.example.lastproject.domain.partymember.repository.PartyMemberRepository;
import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.entity.Penalty;
import com.example.lastproject.domain.penalty.repository.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PenaltyServiceImpl implements PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final PartyRepository partyRepository;
    private final PartyMemberRepository partyMemberRepository;

    /**
     * 페널티 부여
     *
     * @param partyId 페널티를 부여할 유저가 속했던 파티
     * @param request 페널티를 부여할 유저의 리스트
     */
    @Override
    @Transactional
    public void sendPenalty(AuthUser authUser, Long partyId, PenaltyRequest request) {

        // 파티 찾기
        Party party = partyRepository.findByIdAndPartyStatus(partyId, PartyStatus.DONE)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.PARTY_NOT_FOUND)
                );

        // 해당 파티의 해당 유저아이디와 리더 롤을 가진 사람 찾기, 그 사람이 없거나 아니라면 예외처리
        Optional<PartyMember> partyLeader = partyMemberRepository
                .findByPartyIdAndUserIdAndRole(
                        partyId,
                        authUser.getUserId(),
                        PartyMemberRole.LEADER
                );

        if (partyLeader.isEmpty()) {
            throw new CustomException(ErrorCode.USER_IS_NOT_LEADER);
        }

        List<PartyMember> members = partyMemberRepository
                .findByPartyIdAndUserIdInAndRoleNot(
                        partyId,
                        request.getUserIds(),
                        PartyMemberRole.LEADER
                );
        List<Penalty> penalties = new ArrayList<>();

        // 지정된 유저를 대상으로 각각 페널티를 부여하고 리스트에 추가
        for (PartyMember user : members) {

            Penalty penalty = new Penalty(party, user.getUser());
            penalties.add(penalty);
        }

        // 추가한 페널티 리스트를 한번에 save
        penaltyRepository.saveAll(penalties);

    }

}
