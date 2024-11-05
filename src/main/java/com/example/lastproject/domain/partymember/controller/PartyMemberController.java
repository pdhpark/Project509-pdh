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
     * 파티원 : 파티에 참가 신청하는 메소드
     *
     * @param partyId 파티의 ID
     * @param authUser 파티에 참가 신청하는 유저 확인(파티원)
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
     * 파티장 : 특정 파티의 참가 신청 목록 조회 메소드
     *
     * @param partyId 파티의 ID
     * @return ResponseEntity<List<PartyMemberUpdateRequest>> 참가 신청 목록과 HTTP 상태 코드 200 반환
     */
    @GetMapping("/{partyId}/join-requests")
    public ResponseEntity<List<PartyMemberUpdateRequest>> getJoinRequests(
            @PathVariable Long partyId) {
        List<PartyMemberUpdateRequest> joinRequests = partyMemberService.getJoinRequests(partyId);
        return ResponseEntity.ok(joinRequests);
    }

    /**
     * 파티장 : 초대 상태 업데이트 메소드
     *
     * @param partyMemberId 파티 멤버의 ID
     * @param request 초대 상태 변경 시 필요한 json body
     *                (inviteStatus: ACCEPTED(승인), REJECTED(거절))
     * @return ResponseEntity<Void> 상태 코드 204(No Content) 반환
     */
    @PutMapping("/{partyMemberId}/invite-status")
    public ResponseEntity<Void> updateInviteStatus(
            @PathVariable Long partyMemberId,
            @RequestBody PartyMemberUpdateRequest request) {
        partyMemberService.updateInviteStatus(partyMemberId, request.getInviteStatus());
        return ResponseEntity.noContent().build();
    }

}
