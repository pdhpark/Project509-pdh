package com.example.lastproject.domain.likeitem.controller;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.likeitem.dto.response.LikeItemResponseDto;
import com.example.lastproject.domain.likeitem.service.LikeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like-items")
public class LikeItemController {

    private final LikeItemService likeItemService;

    /**
     * 즐겨찾기 품목 등록
     * @param authUser 즐겨찾기 품목 등록할 유저 대상
     * @param itemId 즐겨찾기 등록할 품목 아이디
     * @return 성공메시지
     */
    @PostMapping("/{itemId}")
    public ResponseEntity<String> bookmarkItem(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long itemId) {

        likeItemService.bookmarkItem(authUser, itemId);

        return ResponseEntity.ok("성공임시메시지");
    }

    /**
     * 즐겨찾기 품목 리스트 조회
     * @param authUser 품목 조회할 유저 대상
     * @return 조회된 즐겨찾기 품록 객체리스트
     */
    @GetMapping
    public ResponseEntity<LikeItemResponseDto> getLikeItems(@AuthenticationPrincipal AuthUser authUser) {

        LikeItemResponseDto response = likeItemService.getLikeItems(authUser);

        return ResponseEntity.ok(response);
    }

}
