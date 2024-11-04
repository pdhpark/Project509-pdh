package com.example.lastproject.domain.penalty.controller;

import com.example.lastproject.common.dto.AuthUser;
import com.example.lastproject.domain.penalty.dto.request.PenaltyRequest;
import com.example.lastproject.domain.penalty.dto.response.PenaltyResponse;
import com.example.lastproject.domain.penalty.service.PenaltyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parties/{partyId}")
public class PenaltyController {

    private final PenaltyServiceImpl penaltyService;

    /**
     * 페널티 부여
     *
     * @param authUser 인증된 사용자
     * @param partyId  페널티를 받을 유저가 있는 파티
     * @param request  페널티를 부여할 유저 리스트
     * @return 응답 객체
     */
    @PostMapping("/penalties")
    public ResponseEntity<PenaltyResponse> sendPenalty(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable("partyId") Long partyId,
            @RequestBody PenaltyRequest request
    ) {

        penaltyService.sendPenalty(authUser, partyId, request);
        PenaltyResponse response = new PenaltyResponse();
        return ResponseEntity.ok().
                body(response);

    }

}
