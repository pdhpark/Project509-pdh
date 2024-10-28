package com.example.lastproject.domain.partymember.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.partymember.entity.PartyMember;
import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import com.example.lastproject.domain.partymember.enums.PartyMemberRole;
import com.example.lastproject.domain.partymember.repository.PartyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;

    public PartyMemberService(PartyMemberRepository partyMemberRepository) {
        this.partyMemberRepository = partyMemberRepository;
    }

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

}
