package com.example.lastproject.domain.likeitem.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.service.ItemService;
import com.example.lastproject.domain.likeitem.dto.response.LikeItemResponseDto;
import com.example.lastproject.domain.likeitem.entity.LikeItem;
import com.example.lastproject.domain.likeitem.repository.LikeItemRepository;
import com.example.lastproject.domain.user.entity.User;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LikeItemService {
    private final LikeItemRepository likeItemRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Transactional
    public void bookmarkItem(AuthUser authUser, Long itemId) {

        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Item item = itemService.validateEntity(itemId);

        LikeItem likeItem = new LikeItem(user, item);

        likeItemRepository.save(likeItem);
    }

    public LikeItemResponseDto getLikeItems(AuthUser authUser) {

        Long userId = authUser.getUserId();

        return null;
    }

}
