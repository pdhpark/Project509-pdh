package com.example.lastproject.domain.penalty.controller;

import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyService penaltyService;

    @PostMapping("/party/{partyId}/penalty")
    public ResponseEntity<String> sendPenalty(
            @PathVariable("partyId") Long partyId,
            @RequestBody PenaltyRequest request
    ) {
        penaltyService.sendPenalty(partyId, request);
        return ResponseEntity.ok().
                body("해당 유저에게 페널티를 부과했습니다.");
    }
}
