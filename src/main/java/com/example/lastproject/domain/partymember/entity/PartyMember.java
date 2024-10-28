package com.example.lastproject.domain.partymember.entity;

import com.example.lastproject.domain.party.entity.Party;
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

    // 초대 보류가 기본 값
    public PartyMember(User user, Party party, PartyMemberRole role) {
        this.user = user;
        this.party = party;
        this.role = role;
        this.inviteStatus = PartyMemberInviteStatus.PENDING;
    }

    public PartyMember updateInviteStatus(PartyMemberInviteStatus newStatus) {
        this.inviteStatus = newStatus;
        return this;
    }

    public PartyMember updateRole(PartyMemberRole newRole) {
        this.role = newRole;
        return this;
    }

    // 초대 수락
    public void acceptInvite() {
        this.inviteStatus = PartyMemberInviteStatus.ACCEPTED;
    }

    // 초대 거절
    public void rejectInvite() {
        this.inviteStatus = PartyMemberInviteStatus.REJECTED;
    }

    // 리더 확인
    public boolean isLeader() {
        return this.role == PartyMemberRole.LEADER;
    }

    // 멤버 확인
    public boolean isMember() {
        return this.role == PartyMemberRole.MEMBER;
    }

}
