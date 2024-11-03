package com.example.lastproject.domain.partymember.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPartyMember is a Querydsl query type for PartyMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartyMember extends EntityPathBase<PartyMember> {

    private static final long serialVersionUID = -52264463L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPartyMember partyMember = new QPartyMember("partyMember");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus> inviteStatus = createEnum("inviteStatus", com.example.lastproject.domain.partymember.enums.PartyMemberInviteStatus.class);

    public final com.example.lastproject.domain.party.entity.QParty party;

    public final EnumPath<com.example.lastproject.domain.partymember.enums.PartyMemberRole> role = createEnum("role", com.example.lastproject.domain.partymember.enums.PartyMemberRole.class);

    public final com.example.lastproject.domain.user.entity.QUser user;

    public QPartyMember(String variable) {
        this(PartyMember.class, forVariable(variable), INITS);
    }

    public QPartyMember(Path<? extends PartyMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPartyMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPartyMember(PathMetadata metadata, PathInits inits) {
        this(PartyMember.class, metadata, inits);
    }

    public QPartyMember(Class<? extends PartyMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.party = inits.isInitialized("party") ? new com.example.lastproject.domain.party.entity.QParty(forProperty("party"), inits.get("party")) : null;
        this.user = inits.isInitialized("user") ? new com.example.lastproject.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

