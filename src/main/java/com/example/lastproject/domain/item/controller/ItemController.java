package com.example.lastproject.domain.item.controller;

import com.example.lastproject.domain.item.service.ItemOpenApiService;
import com.example.lastproject.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/items")
@RestController
public class ItemController {
    private final ItemOpenApiService itemOpenApiService;
    private final ItemService itemService;

    // 오픈 API 로부터 표준품목데이터 저장
    @GetMapping("/open-api")
    public ResponseEntity<String> getItemFromOpenApi(){
        return ResponseEntity.ok(itemOpenApiService.getItemFromOpenApi());
    }

    @GetMapping("/test")
    public void testService(){
        itemService.testApi();
    }
}
