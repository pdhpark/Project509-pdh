package com.example.lastproject.domain.party.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NearByPartyResponse {

    private final long id;
    private final String marketName;
    private final String marketAddress;
    private final BigDecimal locationRange;
    private final long itemId;

    @QueryProjection
    public NearByPartyResponse(Long id, String marketName, String marketAddress, BigDecimal locationRange, Long itemId) {
        this.id = id;
        this.marketName = marketName;
        this.marketAddress = marketAddress;
        this.locationRange = locationRange;
        this.itemId = itemId;
    }

}
