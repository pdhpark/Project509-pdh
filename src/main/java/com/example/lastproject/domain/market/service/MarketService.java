package com.example.lastproject.domain.market.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.market.dto.request.MarketRequestDto;
import com.example.lastproject.domain.market.entity.Market;
import com.example.lastproject.domain.market.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MarketService {

    private final MarketRepository marketRepository;

    @Transactional
    // 마켓 저장
    public String saveMarket(MarketRequestDto requestDto) {
        marketRepository.save(requestDto.toEntity());

        return "성공 임시메시지";
    }

    @Transactional
    // 마켓 삭제
    public String deleteMarket(Long marketId) {
        Market market = marketRepository.findById(marketId).orElseThrow(
                () -> new CustomException(ErrorCode.MARKET_NOT_FOUND)
        );

        marketRepository.delete(market);

        return "성공 임시메시지";
    }
}
