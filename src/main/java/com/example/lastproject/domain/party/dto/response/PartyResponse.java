package com.example.lastproject.domain.party.dto.response;

import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PartyResponse {
    private Long id;
    private String marketName;
    private String marketAddress;
    private Long itemId;
    private String itemUnit;
    private String formattedStartTime;
    private String formattedEndTime;
    private int maxMembers;
    private PartyStatus partyStatus;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

    public PartyResponse(Party party) {
        this.id = party.getId();
        this.marketName = party.getMarketName();
        this.marketAddress = party.getMarketAddress();
        this.itemId = party.getItem().getId();
        this.itemUnit = party.getItemUnit();
        this.formattedStartTime = party.getStartTime().format(formatter);
        this.formattedEndTime = party.getEndTime().format(formatter);
        this.maxMembers = party.getMaxMembers();
        this.partyStatus = party.getPartyStatus();
    }
}
