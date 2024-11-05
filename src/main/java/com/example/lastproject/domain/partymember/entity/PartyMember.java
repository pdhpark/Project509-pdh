package com.example.lastproject.domain.partymember.entity;

import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus;
import com.example.lastproject.domain.partymember.enums.PartyMemberRole;
import com.example.lastproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PartyMember")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartyMemberInviteStatus inviteStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartyMemberRole role;

    @Enumerated(EnumType.STRING)
    private PartyStatus status;

    // 파티원이 참가 신청을 누르면 기본값은 PENDING(보류) 상태
    public PartyMember(User user, Party party, PartyMemberRole role) {
        this.user = user;
        this.party = party;
        this.role = role;
        this.inviteStatus = PartyMemberInviteStatus.PENDING;
    }

    // 파티장이 들어온 신청서를 수락하거나 거절 할 때
    public PartyMember(User user, Party party, PartyMemberRole role, PartyMemberInviteStatus inviteStatus) {
        this.user = user;
        this.party = party;
        this.role = role;
        this.inviteStatus = inviteStatus;
    }

    public PartyMember updateInviteStatus(PartyMemberInviteStatus newStatus) {
        this.inviteStatus = newStatus;
        return this;
    }

    public PartyMember updateRole(PartyMemberRole newRole) {
        this.role = newRole;
        return this;
    }

    // 파티장 : 파티 신청 수락
    public void acceptInvite() {
        this.inviteStatus = PartyMemberInviteStatus.ACCEPTED;
    }

    // 파티장 : 파티 신청 거절
    public void rejectInvite() {
        this.inviteStatus = PartyMemberInviteStatus.REJECTED;
    }

    // 파티장(리더) 확인
    public boolean isLeader() {
        return this.role == PartyMemberRole.LEADER;
    }

    // 파티원(멤버) 확인
    public boolean isMember() {
        return this.role == PartyMemberRole.MEMBER;
    }

}
