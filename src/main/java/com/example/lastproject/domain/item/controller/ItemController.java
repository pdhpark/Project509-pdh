package com.example.lastproject.domain.item.controller;

import com.example.lastproject.domain.item.dto.request.ItemRequestDto;
import com.example.lastproject.domain.item.service.ItemOpenApiService;
import com.example.lastproject.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/items")
@RestController
public class ItemController {

    private final ItemOpenApiService itemOpenApiService;
    private final ItemService itemService;

    // 표준품목 데이터 업데이트
    @PostMapping("/open-api")
    public ResponseEntity<String> getItemFromOpenApi(){
        return ResponseEntity.ok(itemOpenApiService.getItemFromOpenApi());
    }
    // 품목 추가
    @PostMapping()
    public ResponseEntity<String> addItem(@RequestBody ItemRequestDto requestDto){
        return ResponseEntity.ok(itemService.addItem(requestDto));
    }
    // 품목 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId ){
        return ResponseEntity.ok(itemService.deleteItem(itemId));
    }
}
