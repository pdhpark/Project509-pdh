package com.example.lastproject.domain.item.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.item.dto.response.ItemResponse;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> searchItems(String keyword) {

        List<ItemResponse> results = itemRepository.searchItemsByKeywordInCategory(keyword);

        // 조회되는 품목이 없으면 예외문 반환
        if (results.isEmpty()) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        return results;
    }

    // 재사용 잦은 코드 메서드 분리
    public Item validateEntity(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        return item;
    }

}
