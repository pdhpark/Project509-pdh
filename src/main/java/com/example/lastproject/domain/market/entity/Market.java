package com.example.lastproject.domain.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marketName;

    @Column(nullable = false)
    private String address;

    public Market(String marketName, String address) {
        this.marketName = marketName;
        this.address = address;
    }

}
