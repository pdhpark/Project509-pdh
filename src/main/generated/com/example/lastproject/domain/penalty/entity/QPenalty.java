package com.example.lastproject.domain.penalty.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPenalty is a Querydsl query type for Penalty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPenalty extends EntityPathBase<Penalty> {

    private static final long serialVersionUID = 2018023985L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPenalty penalty = new QPenalty("penalty");

    public final com.example.lastproject.common.QTimestamped _super = new com.example.lastproject.common.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.example.lastproject.domain.party.entity.QParty partyId;

    public final EnumPath<com.example.lastproject.domain.penalty.enums.PenaltyStatus> status = createEnum("status", com.example.lastproject.domain.penalty.enums.PenaltyStatus.class);

    public final com.example.lastproject.domain.user.entity.QUser userId;

    public QPenalty(String variable) {
        this(Penalty.class, forVariable(variable), INITS);
    }

    public QPenalty(Path<? extends Penalty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPenalty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPenalty(PathMetadata metadata, PathInits inits) {
        this(Penalty.class, metadata, inits);
    }

    public QPenalty(Class<? extends Penalty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partyId = inits.isInitialized("partyId") ? new com.example.lastproject.domain.party.entity.QParty(forProperty("partyId"), inits.get("partyId")) : null;
        this.userId = inits.isInitialized("userId") ? new com.example.lastproject.domain.user.entity.QUser(forProperty("userId")) : null;
    }

}

