package com.example.lastproject.domain.likeitem.service;

import com.example.lastproject.domain.likeitem.repository.LikeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeItemService {
    private final LikeItemRepository likeItemRepository;

    public Object getItems(String keyword) {
        return null;
    }
}
