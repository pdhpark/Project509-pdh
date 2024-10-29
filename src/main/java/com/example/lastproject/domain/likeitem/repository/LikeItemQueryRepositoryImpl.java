package com.example.lastproject.domain.likeitem.repository;

import com.example.lastproject.domain.likeitem.dto.response.LikeItemResponse;
import com.example.lastproject.domain.likeitem.dto.response.QLikeItemResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.lastproject.domain.likeitem.entity.QLikeItem.likeItem;

@Repository
@RequiredArgsConstructor
public class LikeItemQueryRepositoryImpl implements LikeItemQueryRepository{

    private final JPAQueryFactory q;

    @Override
    public List<LikeItemResponse> getBookmarkedItems(long bookmarkId) {
        List<LikeItemResponse> results = q
                .select(
                        new QLikeItemResponse(likeItem.item.id, likeItem.item.category)
                )
                .from(likeItem)
                .where(likeItem.user.id.eq(bookmarkId))
                .fetch();

        return results;
    }

}
