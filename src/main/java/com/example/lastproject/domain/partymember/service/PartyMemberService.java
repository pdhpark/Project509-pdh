package com.example.lastproject.domain.partymember.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.repository.PartyRepository;
import com.example.lastproject.domain.partymember.entity.PartyMember;
import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import com.example.lastproject.domain.partymember.enums.PartyMemberRole;
import com.example.lastproject.domain.partymember.repository.PartyMemberRepository;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;
    private final PartyRepository partyRepository;
    private final UserRepository userRepository;


    // 초대 상태 업데이트
    public void updateInviteStatus(Long partyMemberId, PartyMemberInviteStatus newStatus) {
        PartyMember partyMember = partyMemberRepository.findById(partyMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_MEMBER_NOT_FOUND));

        partyMember.updateInviteStatus(newStatus);
    }

    // 파티창이 생성된 후 역할 업데이트
    public void updateRole(Long partyMemberId, PartyMemberRole newRole) {
        PartyMember partyMember = partyMemberRepository.findById(partyMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_MEMBER_NOT_FOUND));
        partyMember.updateRole(newRole);
    }

    // 파티원 : 파티에 참가 신청
    public void sendJoinRequest(Long partyId, Long userId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        PartyMember partyMember = new PartyMember(user, party, PartyMemberRole.MEMBER);
        partyMemberRepository.save(partyMember);
    }

}
