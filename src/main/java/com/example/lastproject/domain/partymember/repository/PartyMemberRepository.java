package com.example.lastproject.domain.partymember.repository;

import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import com.example.lastproject.domain.partymember.entity.PartyMember;
import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {

    List<PartyMember> findByInviteStatus(PartyMemberInviteStatus status);
    List<PartyMember> findByPartyId(Long partyId);
    Optional<PartyMember> findByPartyIdAndUserId(Long partyId, Long userId);
    List<PartyMember> findByUserId(Long userId);
    List<Party> findAllByStatus(PartyStatus status);

}
