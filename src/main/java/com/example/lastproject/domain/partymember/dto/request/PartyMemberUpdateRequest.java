package com.example.lastproject.domain.partymember.dto.request;

import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartyMemberUpdateRequest {

    private Long userId;
    private PartyMemberInviteStatus inviteStatus;

}
