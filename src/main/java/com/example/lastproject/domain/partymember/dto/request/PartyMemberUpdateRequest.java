package com.example.lastproject.domain.partymember.dto.request;

import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartyMemberUpdateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private PartyMemberInviteStatus inviteStatus;

}
