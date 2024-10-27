package com.example.lastproject.domain.party.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyUpdateRequest {
    private Long itemId;
    private Integer maxMembers;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
