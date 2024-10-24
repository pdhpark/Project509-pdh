package com.example.lastproject.domain.likeitem.controller;

import com.example.lastproject.domain.likeitem.service.LikeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like-items")
public class LikeItemController {
    private final LikeItemService likeItemService;


    // 아이템 품목조회
    @GetMapping
    public ResponseEntity<?> getItems(@RequestParam String keyword) {

        return ResponseEntity.ok(likeItemService.getItems(keyword));
    }
}
