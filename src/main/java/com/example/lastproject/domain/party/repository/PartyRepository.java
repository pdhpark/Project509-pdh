package com.example.lastproject.domain.party.repository;

import com.example.lastproject.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
