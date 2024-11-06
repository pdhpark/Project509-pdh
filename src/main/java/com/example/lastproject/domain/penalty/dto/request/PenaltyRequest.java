package com.example.lastproject.domain.penalty.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PenaltyRequest {

    private List<Long> userIds;

    public PenaltyRequest(List<Long> userIds) {
        this.userIds = userIds;
    }

    public PenaltyRequest() {
    }

}
