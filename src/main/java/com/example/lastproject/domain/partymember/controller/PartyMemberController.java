package com.example.lastproject.domain.partymember.controller;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.partymember.dto.request.PartyMemberUpdateRequest;
import com.example.lastproject.domain.partymember.service.PartyMemberService;
import com.example.lastproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party-members")
public class PartyMemberController {

    private final PartyMemberService partyMemberService;
    private final UserService userService;

    /**
     * 초대 상태 업데이트 메소드
     * @param partyMemberId 파티 멤버의 ID
     * @param request 초대 상태 변경 시 필요한 json body
     *        (inviteStatus: ACCEPTED(승인), REJECTED(거절)
     * @return HTTP 상태 코드 204(No Content)를 반환
     */
    @PutMapping("/{partyMemberId}/invite-status")
    public ResponseEntity<Void> updateInviteStatus(
            @PathVariable Long partyMemberId,
            @RequestBody PartyMemberUpdateRequest request) {
        partyMemberService.updateInviteStatus(partyMemberId, request.getInviteStatus());
        return ResponseEntity.noContent().build();
    }

    /**
     * 파티에 참가 신청 메소드
     * @param
     * @return
     */
    @PostMapping("/{partyId}/join")
    public ResponseEntity<Void> joinParty(
            @PathVariable Long partyId,
            @AuthenticationPrincipal AuthUser authUser) {
        Long userId = authUser.getUserId();
        partyMemberService.sendJoinRequest(partyId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
