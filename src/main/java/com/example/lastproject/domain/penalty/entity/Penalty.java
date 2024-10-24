package com.example.lastproject.domain.penalty.entity;

import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Penalty extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Party partyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private User userId;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Penalty(Party partyId, User userId) {
        this.partyId = partyId;
        this.userId = userId;
    }
}
