package com.example.lastproject.domain.party.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PartyCreateRequest {
    private Long marketId;
    private Long itemId;
    private String itemUnit;
    private int maxMembers;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}


