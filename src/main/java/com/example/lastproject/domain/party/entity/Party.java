package com.example.lastproject.domain.party.entity;

import com.example.lastproject.common.Timestamped;
import com.example.lastproject.domain.item.entity.Item;
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

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @Column(name = "market_address", nullable = false)
    private String marketAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "item_unit", nullable = false)
    private String itemUnit;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int maxMembers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartyStatus partyStatus = PartyStatus.OPEN;

    public Party(String marketName, String marketAddress, Item item, String itemUnit, LocalDateTime startTime, LocalDateTime endTime, int maxMembers) {
        this.marketName = marketName;
        this.marketAddress = marketAddress;
        this.item = item;
        this.itemUnit = itemUnit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxMembers = maxMembers;
        this.partyStatus = PartyStatus.OPEN;
    }

    public void updatePartyStatus(PartyStatus newStatus) {
        this.partyStatus = newStatus;
    }

    public void updateMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }
}
