package com.example.lastproject.domain.item.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.lastproject.domain.item.dto.response.QItemResponse is a Querydsl Projection type for ItemResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QItemResponse extends ConstructorExpression<ItemResponse> {

    private static final long serialVersionUID = 1084695045L;

    public QItemResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> category, com.querydsl.core.types.Expression<String> productName) {
        super(ItemResponse.class, new Class<?>[]{long.class, String.class, String.class}, id, category, productName);
    }

}

