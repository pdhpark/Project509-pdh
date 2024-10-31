package com.example.lastproject.domain.item.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ItemResponse {

    private final long id;
    private final String category;
    private final String productName;

    @QueryProjection
    public ItemResponse(long id, String category, String productName) {
        this.id = id;
        this.category = category;
        this.productName = productName;
    }

}
