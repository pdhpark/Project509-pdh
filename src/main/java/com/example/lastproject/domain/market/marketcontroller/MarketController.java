package com.example.lastproject.domain.market.marketcontroller;

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

    @PostMapping
    public ResponseEntity<String> saveMarket(@RequestBody MarketRequestDto requestDto){
        return ResponseEntity.ok(marketService.saveMarket(requestDto));
    }

    @DeleteMapping("/{marketId}")
    public ResponseEntity<String> deleteMarket(@PathVariable Long marketId){
        return ResponseEntity.ok(marketService.deleteMarket(marketId));
    }
}
