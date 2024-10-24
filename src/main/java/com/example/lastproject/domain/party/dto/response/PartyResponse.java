package com.example.lastproject.domain.party.dto.response;

import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PartyResponse {
    private Long id;
    private Long marketId;
    private Long itemId;
    private String itemUnit;
    private int maxMembers;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private PartyStatus partyStatus;

    public PartyResponse(Party party) {
        this.id = party.getId();
        this.marketId = party.getMarket().getId();
        this.itemId = party.getItem().getId();
        this.itemUnit = party.getItemUnit();
        this.maxMembers = party.getMaxMembers();
        this.startTime = party.getStartTime();
        this.endTime = party.getEndTime();
        this.partyStatus = party.getPartyStatus();
    }
}
