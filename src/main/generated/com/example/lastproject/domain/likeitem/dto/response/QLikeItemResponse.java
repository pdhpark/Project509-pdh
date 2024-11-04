package com.example.lastproject.domain.likeitem.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.lastproject.domain.likeitem.dto.response.QLikeItemResponse is a Querydsl Projection type for LikeItemResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QLikeItemResponse extends ConstructorExpression<LikeItemResponse> {

    private static final long serialVersionUID = -1709851245L;

    public QLikeItemResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> category, com.querydsl.core.types.Expression<String> productName) {
        super(LikeItemResponse.class, new Class<?>[]{long.class, String.class, String.class}, id, category, productName);
    }

}

