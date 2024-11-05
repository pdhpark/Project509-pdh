package com.example.lastproject.domain.party.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParty is a Querydsl query type for Party
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParty extends EntityPathBase<Party> {

    private static final long serialVersionUID = 325066449L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParty party = new QParty("party");

    public final com.example.lastproject.common.QTimestamped _super = new com.example.lastproject.common.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.lastproject.domain.item.entity.QItem item;

    public final NumberPath<Integer> itemCount = createNumber("itemCount", Integer.class);

    public final StringPath itemUnit = createString("itemUnit");

    public final StringPath marketAddress = createString("marketAddress");

    public final StringPath marketName = createString("marketName");

    public final NumberPath<Integer> membersCount = createNumber("membersCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.example.lastproject.domain.partymember.entity.PartyMember, com.example.lastproject.domain.partymember.entity.QPartyMember> partyMembers = this.<com.example.lastproject.domain.partymember.entity.PartyMember, com.example.lastproject.domain.partymember.entity.QPartyMember>createList("partyMembers", com.example.lastproject.domain.partymember.entity.PartyMember.class, com.example.lastproject.domain.partymember.entity.QPartyMember.class, PathInits.DIRECT2);

    public final EnumPath<com.example.lastproject.domain.party.enums.PartyStatus> partyStatus = createEnum("partyStatus", com.example.lastproject.domain.party.enums.PartyStatus.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public QParty(String variable) {
        this(Party.class, forVariable(variable), INITS);
    }

    public QParty(Path<? extends Party> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParty(PathMetadata metadata, PathInits inits) {
        this(Party.class, metadata, inits);
    }

    public QParty(Class<? extends Party> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.example.lastproject.domain.item.entity.QItem(forProperty("item")) : null;
    }

}

