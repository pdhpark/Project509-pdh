package com.example.lastproject.domain.penalty.entity;

import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.penalty.enums.PenaltyStatus;
import com.example.lastproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "penalty")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Penalty extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party partyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Enumerated(EnumType.STRING)
    private PenaltyStatus status = PenaltyStatus.SEARCHABLE; // 3개월이 지나면 조회 불가

    public Penalty(Party partyId, User userId) {
        this.partyId = partyId;
        this.userId = userId;
    }

}
