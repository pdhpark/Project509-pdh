package com.example.lastproject.domain.partymember.controller;

import com.example.lastproject.common.dto.AuthUser;
import com.example.lastproject.domain.partymember.dto.request.PartyMemberUpdateRequest;
import com.example.lastproject.domain.partymember.service.PartyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/party-members")
public class PartyMemberController {

    private final PartyMemberService partyMemberService;

    /**
     * 파티원: 파티에 참가 신청
     *
     * @param partyId 파티의 ID
     * @param authUser 파티에 참가 신청하는 유저 정보 (파티원)
     * @return ResponseEntity<Void> 상태 코드 201(CREATED) 반환
     */
    @PostMapping("/{partyId}/join")
    public ResponseEntity<Void> joinParty(
            @PathVariable Long partyId,
            @AuthenticationPrincipal AuthUser authUser) {
        Long userId = authUser.getUserId();
        partyMemberService.sendJoinRequest(partyId, authUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 파티장 : 내가 생성한 파티에 참가 신청한 유저 목록 조회
     *
     * @param partyId 파티의 ID
     * @return ResponseEntity<List<PartyMemberUpdateRequest>> 승인 대기 중인 참가 신청 목록과 HTTP 상태 코드 200 반환
     */
    @GetMapping("/{partyId}/join-requests")
    public ResponseEntity<List<PartyMemberUpdateRequest>> getJoinRequests(
            @PathVariable Long partyId) {
        List<PartyMemberUpdateRequest> joinRequests = partyMemberService.getJoinRequests(partyId);
        return ResponseEntity.ok(joinRequests);
    }

}
