package com.example.lastproject.domain.partymember.dto.request;

import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyMemberUpdateRequest {

    private PartyMemberInviteStatus inviteStatus;

}
