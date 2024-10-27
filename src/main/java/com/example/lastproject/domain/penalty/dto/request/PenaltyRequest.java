package com.example.lastproject.domain.penalty.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PenaltyRequest {

    private List<Long> userIds;
}
