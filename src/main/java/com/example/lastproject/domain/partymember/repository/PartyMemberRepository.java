package com.example.lastproject.domain.partymember.repository;

import com.example.lastproject.domain.partymember.entity.PartyMember;
import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {

    List<PartyMember> findByInviteStatus(PartyMemberInviteStatus status);
    List<PartyMember> findByPartyId(Long partyId);

}
