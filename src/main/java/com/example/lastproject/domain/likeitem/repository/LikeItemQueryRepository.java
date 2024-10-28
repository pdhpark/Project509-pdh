package com.example.lastproject.domain.likeitem.repository;

import com.example.lastproject.domain.item.dto.response.ItemResponseDto;

import java.util.List;

public interface LikeItemQueryRepository {

    List<ItemResponseDto> getBookmarkedItems();

}
