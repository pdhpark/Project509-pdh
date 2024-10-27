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

    /**
     * 표준 품목 업데이트
     * @return 성공메시지
     */
    @PostMapping("/open-api")
    public ResponseEntity<String> getItemFromOpenApi(){
        return ResponseEntity.ok(itemOpenApiService.getItemFromOpenApi());
    }

    /**
     *
     * @param requestDto 추가할 품목 데이터 객체
     * @return 성공메시지
     */
    @PostMapping()
    public ResponseEntity<String> addItem(@RequestBody ItemRequestDto requestDto){
        return ResponseEntity.ok(itemService.addItem(requestDto));
    }

    /**
     *
     * @param itemId 품목 삭제할 아이디값
     * @return 성공메시지
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId ){
        return ResponseEntity.ok(itemService.deleteItem(itemId));
    }
}
