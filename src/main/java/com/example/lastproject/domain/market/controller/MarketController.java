package com.example.lastproject.domain.market.controller;

import com.example.lastproject.domain.market.dto.request.MarketRequestDto;
import com.example.lastproject.domain.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/markets")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    /**
     * 마켓 등록
     * @param requestDto 등록할 마켓 객체데이터
     * @return 성공메시지
     */
    @PostMapping
    public ResponseEntity<String> saveMarket(@RequestBody MarketRequestDto requestDto){
        return ResponseEntity.ok(marketService.saveMarket(requestDto));
    }

    /**
     * 마켓 삭제
     * @param marketId 삭제할 마켓 아이디
     * @return 성공 메시지
     */
    @DeleteMapping("/{marketId}")
    public ResponseEntity<String> deleteMarket(@PathVariable Long marketId){
        return ResponseEntity.ok(marketService.deleteMarket(marketId));
    }

}
