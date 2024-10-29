package com.example.lastproject.domain.likeitem.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class LikeItemResponse {
    private final long id;
    private final String category;

    @QueryProjection
    public LikeItemResponse(long id, String category) {
        this.id = id;
        this.category = category;
    }

}
