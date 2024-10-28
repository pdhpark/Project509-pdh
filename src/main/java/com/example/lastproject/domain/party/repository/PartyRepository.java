package com.example.lastproject.domain.party.repository;

import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findAllByPartyStatus(PartyStatus partyStatus);

}
