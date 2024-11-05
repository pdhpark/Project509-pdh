package com.example.lastproject.domain.penalty.service;

import com.example.lastproject.common.dto.AuthUser;
import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;

public interface PenaltyService {

    void sendPenalty(AuthUser authUser, Long partyId, PenaltyRequest request);

}
