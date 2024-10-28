package com.example.lastproject.domain.penalty.controller;

import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.dto.response.PenaltyResponse;
import com.example.lastproject.domain.penalty.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parties/{partyId}")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @PostMapping("/penalties")
    public ResponseEntity<PenaltyResponse> sendPenalty(
            @PathVariable("partyId") Long partyId,
            @RequestBody PenaltyRequest request
    ) {

        penaltyService.sendPenalty(partyId, request);
        PenaltyResponse response = new PenaltyResponse();
        return ResponseEntity.ok().
                body(response);

    }

}
