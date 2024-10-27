package com.example.lastproject.domain.item.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.item.dto.request.ItemRequestDto;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public String addItem(ItemRequestDto requestDto) {
        itemRepository.save(requestDto.toEntity());
        return "성공 임시메시지";
    }

    @Transactional
    public String deleteItem(Long itemId) {
        Item item = validateEntity(itemId);

        itemRepository.delete(item);
        return "성공 임시메시지";
    }

    // 재사용 잦은 코드 메서드 분리
    public Item validateEntity(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        return item;
    }
}
