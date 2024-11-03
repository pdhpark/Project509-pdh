package com.example.lastproject.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 947188929L;

    public static final QUser user = new QUser("user");

    public final com.example.lastproject.common.QTimestamped _super = new com.example.lastproject.common.QTimestamped(this);

    public final StringPath address = createString("address");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.lastproject.domain.likeitem.entity.LikeItem, com.example.lastproject.domain.likeitem.entity.QLikeItem> likeItems = this.<com.example.lastproject.domain.likeitem.entity.LikeItem, com.example.lastproject.domain.likeitem.entity.QLikeItem>createList("likeItems", com.example.lastproject.domain.likeitem.entity.LikeItem.class, com.example.lastproject.domain.likeitem.entity.QLikeItem.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<com.example.lastproject.domain.user.enums.UserRole> userRole = createEnum("userRole", com.example.lastproject.domain.user.enums.UserRole.class);

    public final EnumPath<com.example.lastproject.domain.user.enums.UserStatus> userStatus = createEnum("userStatus", com.example.lastproject.domain.user.enums.UserStatus.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

