package com.example.lastproject.domain.party.entity;

import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.market.entity.Market;
import com.example.lastproject.domain.party.enums.PartyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Party")
public class Party extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private String itemUnit;

    @Column(nullable = false)
    private int maxMembers;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartyStatus partyStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public Party(Market market, Item item, String itemUnit, int maxMembers, LocalDateTime startTime, LocalDateTime endTime, PartyStatus partyStatus) {
        this.market = market;
        this.item = item;
        this.itemUnit = itemUnit;
        this.maxMembers = maxMembers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.partyStatus = partyStatus;
    }


}
