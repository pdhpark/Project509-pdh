package com.example.lastproject.domain.likeitem.repository;

import com.example.lastproject.domain.item.dto.response.ItemResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeItemQueryRepositoryImpl implements LikeItemQueryRepository{

    private final JPAQueryFactory q;

    @Override
    public List<ItemResponseDto> getBookmarkedItems() {
//        List<ItemResponseDto> results = q
//                .selectFrom()
//                .where()
        return null;
    }

}
