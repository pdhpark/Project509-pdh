package com.example.lastproject.domain.item.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ItemResponse {
    private final long id;
    private final String category;

    @QueryProjection
    public ItemResponse(long id, String category) {
        this.id = id;
        this.category = category;
    }

}
