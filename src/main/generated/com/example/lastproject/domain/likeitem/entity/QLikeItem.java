package com.example.lastproject.domain.likeitem.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeItem is a Querydsl query type for LikeItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeItem extends EntityPathBase<LikeItem> {

    private static final long serialVersionUID = 57851807L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeItem likeItem = new QLikeItem("likeItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.lastproject.domain.item.entity.QItem item;

    public final com.example.lastproject.domain.user.entity.QUser user;

    public QLikeItem(String variable) {
        this(LikeItem.class, forVariable(variable), INITS);
    }

    public QLikeItem(Path<? extends LikeItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeItem(PathMetadata metadata, PathInits inits) {
        this(LikeItem.class, metadata, inits);
    }

    public QLikeItem(Class<? extends LikeItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.example.lastproject.domain.item.entity.QItem(forProperty("item")) : null;
        this.user = inits.isInitialized("user") ? new com.example.lastproject.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

