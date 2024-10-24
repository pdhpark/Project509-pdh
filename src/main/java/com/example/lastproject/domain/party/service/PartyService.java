package com.example.lastproject.domain.party.service;

import com.example.lastproject.domain.item.service.ItemService;
import com.example.lastproject.domain.market.service.MarketService;
import com.example.lastproject.domain.party.repository.PartyRepository;
import org.springframework.stereotype.Service;

@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private final MarketService marketService;
    private final ItemService itemService;

    public PartyService(PartyRepository partyRepository, MarketService marketService, ItemService itemService) {
        this.partyRepository = partyRepository;
        this.marketService = marketService;
        this.itemService = itemService;
    }


}
