package com.example.lastproject.domain.item.service;

import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void testApi() {
        Item item = new Item().builder()
                .category("test")
                .productName("test")
                .build();

        itemRepository.save(item);
    }
}
